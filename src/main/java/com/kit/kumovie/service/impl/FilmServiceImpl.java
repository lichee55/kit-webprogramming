package com.kit.kumovie.service.impl;

import com.kit.kumovie.common.Common;
import com.kit.kumovie.domain.*;
import com.kit.kumovie.domain.repository.*;
import com.kit.kumovie.dto.*;
import com.kit.kumovie.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final MemberRepository memberRepository;

    @Override
    public Page<FilmListDTO> getFilmList(Pageable pageable) {
        List<Film> all = filmRepository.findAllByReleaseDateDesc();
        return listToPage(pageable, all);
    }

    @Override
    public Page<FilmListDTO> getFilmListByActor(String actor, Pageable pageable) {
        List<Film> findByActor = filmRepository.findAllByActorContainsIgnoreCase(actor);
        return listToPage(pageable, findByActor);
    }

    @Override
    public Page<FilmListDTO> getFilmListByTitle(String title, Pageable pageable) {
        List<Film> findByTitle = filmRepository.findAllByTitleContainsIgnoreCase(title);
        return listToPage(pageable, findByTitle);
    }

    @Override
    @Transactional
    public Page<FilmListDTO> getNowFilmList(Pageable pageable) {
        List<Film> findByNowScreening = filmRepository.findAllByStartTimeAfter(LocalDateTime.now());
        findByNowScreening.forEach(film -> {
            Integer seatCount = ticketRepository.getSeatCount(LocalDateTime.now(), film.getId());
            film.setSeatCount(Objects.requireNonNullElse(seatCount, 0));
            filmRepository.save(film);
        });
        return listToPage(pageable, findByNowScreening);
    }

    @Override
    public Page<CommentDTO> getFilmComments(Long filmId, Pageable pageable) {
        Page<Comment> findByFilmId = commentRepository.findAllByFilm_IdOrderByCreatedAt(filmId, pageable);
        if (Common.getUserContext() == null) {
            return new PageImpl<>(findByFilmId.getContent().stream().map(CommentDTO::of).collect(Collectors.toList()), pageable, findByFilmId.getTotalElements());
        }
        Long id = Common.getUserContext().getId();
        List<LikeComment> findByUser = likeCommentRepository.findAllByMember_IdOrderByIdDesc(id);
        return findByFilmId.map(comment -> {
            CommentDTO of = CommentDTO.of(comment);
            of.setIsMyComment(id.equals(comment.getMember().getId()));
            of.setIsLiked(findByUser.stream().anyMatch(likeComment -> likeComment.getComment().getId().equals(comment.getId())));
            return of;
        });
    }

    private Page<FilmListDTO> listToPage(Pageable pageable, List<Film> findByNowScreening) {
        Long totalTicketCount = totalTicketCount();
        log.info("pageable.sort: {}", pageable.getSort().toString());
        if (totalTicketCount == null || totalTicketCount == 0) {
            List<FilmListDTO> collect = findByNowScreening.stream().map(FilmListDTO::of).collect(Collectors.toList());
            int offset = pageable.getPageNumber() * pageable.getPageSize();
            int pageEnd = Math.min(offset + pageable.getPageSize(), collect.size());
            return new PageImpl<>(collect.subList(offset, pageEnd), pageable, collect.size());
        }
        List<FilmListDTO> collect1 = findByNowScreening.stream().map(film -> {
            FilmListDTO of = FilmListDTO.of(film);
            BigDecimal ticketRate = BigDecimal.valueOf(film.getSeatCount()).divide(BigDecimal.valueOf(totalTicketCount), MathContext.DECIMAL32);
            of.setTicketRate(ticketRate);
            return of;
        }).collect(Collectors.toList());
        if (pageable.getSort().toString().equals("")) {
            log.info("no sort");
            collect1.sort((o1, o2) -> o2.getTicketRate().compareTo(o1.getTicketRate()));
        } else if (pageable.getSort().toString().equals("ticketRate: DESC")) {
            log.info("ticketRate: DESC");
            collect1.sort((o1, o2) -> o2.getTicketRate().compareTo(o1.getTicketRate()));
        } else if (pageable.getSort().toString().equals("rating: DESC")) {
            log.info("rating: DESC");
            collect1.sort(((o1, o2) -> o2.getRating().compareTo(o1.getRating())));
        }
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int pageEnd = Math.min((offset + pageable.getPageSize()), collect1.size());
        return new PageImpl<>(collect1.subList(offset, pageEnd), pageable, collect1.size());
    }

    @Override
    public FilmDetailDTO getFilmDetail(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
        Long totalTicketCount = totalTicketCount();
        FilmDetailDTO of = FilmDetailDTO.of(film);
        if (totalTicketCount == null || totalTicketCount == 0) {
            of.setTicketRate(BigDecimal.ZERO);
            return of;
        }
        BigDecimal ticketRate = BigDecimal.valueOf(film.getSeatCount()).divide(BigDecimal.valueOf(totalTicketCount), MathContext.DECIMAL32);
        of.setTicketRate(ticketRate);
        return of;
    }

    @Override
    public FilmStatisticDTO getFilmStatistic(Long filmId) {
        Film film = filmRepository.findByIdWithEntityGraph(filmId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
//        Film film = filmRepository.findByIdWithNamedEntityGraph(filmId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));

        List<Member> ticketedMembers = new ArrayList<>();

        film.getScreenings().forEach(sc -> sc.getTickets().forEach(t -> ticketedMembers.add(t.getMember())));

        long maleCount = 0L;
        long[] ageCount = new long[20];


        for (Member member : ticketedMembers) {
            if (member.getGender() == Gender.MALE) {
                maleCount++;
            }
            int i = member.getAge() / 10;
            ageCount[i]++;
        }
        int memberCount = ticketedMembers.size();
        log.info("member count : {}", memberCount);
        if (memberCount == 0) {
            return new FilmStatisticDTO(BigDecimal.ZERO);
        }
        BigDecimal maleRate = BigDecimal.valueOf(maleCount).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32);
        return FilmStatisticDTO.builder()
                .maleRate(maleRate.multiply(BigDecimal.valueOf(100)))
                .femaleRate(BigDecimal.ONE.subtract(maleRate).multiply(BigDecimal.valueOf(100)))
                .age10Rate(BigDecimal.valueOf(ageCount[1]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .age20Rate(BigDecimal.valueOf(ageCount[2]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .age30Rate(BigDecimal.valueOf(ageCount[3]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .age40Rate(BigDecimal.valueOf(ageCount[4]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .age50Rate(BigDecimal.valueOf(ageCount[5]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .build();
    }

    @Override
    @Transactional
    public void writeFilmComment(Long filmId, WriteCommentDTO writeCommentDTO) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
        Member member = memberRepository.findById(Common.getUserContext().getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Comment comment = WriteCommentDTO.toEntity(writeCommentDTO);
        film.setCommentCount(film.getCommentCount() + 1);
        film.setSumRating(film.getSumRating() + comment.getRating());
        comment.setMember(member);
        comment.setFilm(film);
        LocalDateTime now = LocalDateTime.now();
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void updateFilmComment(Long filmId, Long commentId, CommentDTO commentDTO) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if (!comment.getMember().getId().equals(Common.getUserContext().getId())) {
            throw new IllegalArgumentException("자신의 댓글만 수정할 수 있습니다.");
        }
        film.setSumRating(film.getSumRating() - comment.getRating() + commentDTO.getRating());
        comment.setContent(commentDTO.getContent());
        comment.setRating(commentDTO.getRating());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteFilmComment(Long filmId, Long commentId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if (!comment.getMember().getId().equals(Common.getUserContext().getId())) {
            throw new IllegalArgumentException("자신의 댓글만 삭제할 수 있습니다.");
        }
        film.setSumRating(film.getSumRating() - comment.getRating());
        film.setCommentCount(film.getCommentCount() - 1);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public void likeComment(Long filmId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        Member member = memberRepository.findById(Common.getUserContext().getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Optional<LikeComment> like = likeCommentRepository.findByMember_IdAndComment_Id(Common.getUserContext().getId(), commentId);
        if (like.isPresent()) {
            likeCommentRepository.delete(like.get());
            comment.setLikeCount(comment.getLikeCount() - 1);
            return;
        }
        comment.setLikeCount(comment.getLikeCount() + 1);
        LikeComment likeComment = LikeComment.builder()
                .member(member)
                .comment(comment)
                .build();
        likeCommentRepository.save(likeComment);
    }

    private Long totalTicketCount() {
        return ticketRepository.getTotalTicketCount(LocalDateTime.now());
    }
}
