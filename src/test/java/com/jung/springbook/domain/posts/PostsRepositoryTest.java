package com.jung.springbook.domain.posts;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

/**
 * (1) @After 는
 * Junit 에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
 * 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용합니다.
 * 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 있습니다.
 *
 * (2) postsRepository.save 는
 * 테이블 posts 에 insert/update 쿼리를 실행한다.
 * id 값이 있다면 update, 없다면 insert 쿼리가 실행된다.
 *
 * (3) postsRepository.findAll() 는
 * 테이블 posts 에 있는 모든 데이터를 조회해오는 메소드이다.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After // (1)
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder() // (2)
                                    .title(title)
                                    .content(content)
                                    .author("yongjung95@gmail.com")
                                    .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
