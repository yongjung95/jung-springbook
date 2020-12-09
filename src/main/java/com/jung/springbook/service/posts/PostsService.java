package com.jung.springbook.service.posts;

import com.jung.springbook.domain.posts.Posts;
import com.jung.springbook.domain.posts.PostsRepository;
import com.jung.springbook.web.dto.PostsListResponseDto;
import com.jung.springbook.web.dto.PostsResponseDto;
import com.jung.springbook.web.dto.PostsSaveRequestDto;
import com.jung.springbook.web.dto.PostsUpdateRequestDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (1) @Transactional 는
 * @Transactional 이 포함된 메소드가 호출 될 경우, PlatformTransactionManager 를 사용하여 트랜잭션을 시작하고, 정상 여부에 따라 Commit 또는 Rollback 한다.
 *
 * (2) update 쿼리가 없는 이유는
 * 트랜잭션 안에서 데이터베이스에서 데이터를 가져오면 이 데이터는 영속성 컨테스트가 유지된 상태가 되어,
 * 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블(Entity)에 변경분을 반영한다.
 * 즉, Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없다. 이 개념을 더티 체킹이라 한다.
 *
 * (3) stream 은
 * 컬렉션, 배엳등의 저장 요소를 하나씩 참조하며 함수형 인터페이스(람다식)를 적용하며 반복적으로 처리할 수 있도록 해주는 기능이다.
 * 예) 일반 List를 for문 돌려서 사용하는거랑 같음.
 *
 * (4) Map 은
 * stream 의 각 요소를 연산하는데 쓰인다.(각 요소에 접근한다.)
 * PostsListResponseDto::new 은 posts -> new PostsListResponseDto(posts) 와 똑같다.
 */

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional // (1)
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                                    .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent()); // (2)

        return id;
    }

    @Transactional
    public PostsResponseDto findById (Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public List<PostsListResponseDto> findAllDesc(){


        return postsRepository.findAllDesc().stream() // (3)
                .map(PostsListResponseDto::new) // (4)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));

        postsRepository.delete(posts);
    }

}
