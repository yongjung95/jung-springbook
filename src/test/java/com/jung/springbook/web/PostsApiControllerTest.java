package com.jung.springbook.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import com.jung.springbook.domain.posts.Posts;
import com.jung.springbook.domain.posts.PostsRepository;
import com.jung.springbook.web.dto.PostsSaveRequestDto;
import com.jung.springbook.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * (1) @SpringBootTest 는
 * @WebMvcTest 의 경우 JPA 기능이 작동하지 않기때문에 지금 같이 JPA 기능까지 한번에 테스트할 때는
 * @SpringBootTest 와 TestRestTemplate 을 사용하면 된다.
 * SpringBootTest.WebEnvironment.RANDOM_PORT 는 호스트가 사용하지 않는 랜덤 포트를 사용하겠다는 의미
 *
 * (2) @WithMockUser(roles="USER") 는
 * 인증된 모의(가짜) 사용자를 만들어서 사용한다.
 * roles 에 권한을 추가할 수 있다.
 * 즉, 이 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가 API 를 요청하는 것과 동일한 효과를 가진다.
 * 하지만 @WithMockUser 는 MockMvc 에서만 작동하기 때문에, @SpringBootTest 에서 MockMvc 를 사용할 수 있게 설정을 해줘야한다.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // (1)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    @BeforeEach
    public void sertup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }



    @Test
    @WithMockUser(roles="USER") // (2)
    public void Posts_등록된다() throws Exception{

        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                            .title(title)
                                                            .content(content)
                                                            .author("author")
                                                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        // MockMvc를 사용하기 위해 주석 처리

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        // then

        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);
        // MockMvc를 사용하기 위해 주석 처리

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER") // (2)
    public void Posts_수정된다() throws Exception{

        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                                                        .title("title")
                                                    .content("content")
                                                    .author("author")
                                                    .build());

        Long updataId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                                                                .title(expectedTitle)
                                                                .content(expectedContent)
                                                                .build();

        String url = "http://localhost:" + port +"/api/v1/posts/" + updataId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        // ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        // MockMvc를 사용하기 위해 주석 처리

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);
        // MockMvc를 사용하기 위해 주석 처리

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles="USER") // (2)
    public void Posts_삭제된다() throws Exception{

        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long deleteId = savedPosts.getId();

        String url = "http://localhost:" + port +"/api/v1/posts/" + deleteId;

        HttpEntity<Posts> requestEntity = new HttpEntity<>(savedPosts);

        // when
        // ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Long.class);
        // MockMvc를 사용하기 위해 주석 처리

        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(savedPosts)))
                .andExpect(status().isOk());
        // then
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);
        // MockMvc를 사용하기 위해 주석 처리

        List<Posts> all = postsRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

}
