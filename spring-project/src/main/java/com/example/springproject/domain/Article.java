package com.example.springproject.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
@EntityListeners(AuditingEntityListener.class)


public class Article extends AuditingFields{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;        //내가 부여하는게 아닌 영속성할떄 자동 부여하는 번호이기에 Setter로 건드리면 안됨

        @Setter
        @Column(nullable = false)
        private String title;

        @Setter
        @Column(nullable = false,length = 10000)        //필수값 크기는 10000
        private String content;
        @Setter
        private String hashtag;
        //실무에서는 양방향 바인딩을 잘 하지 않는다.
        @OrderBy("id")
        @ToString.Exclude //순환참조 때문에 한쪽에 ToString을 끊는데 보통 One쪽을 끊는다     / cascade로 게시물이 사라지면 댓글도 모두사리ㅏ짐
        @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)        //상대쪽 변수명 이걸하지 않으면 두 엔티티를 합쳐서 테이블 하나를 만든다
        private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
                //중복을 허용하지 않고 여기서 다 보겠다 이런 의도임






        protected Article(){    //code 밖에서 new로 생성하는 걸 방지하기 위해(private는 하이버네이트 구현체 때문에 에러)
                                        //하이버네이트 구현체 떄문에 디폴트 생성자필요
        }

        private Article(String title, String content, String hashtag) {
                this.title = title;                     //이 3가지를 제외한 나머지는 값이 자동생성
                this.content = content;
                this.hashtag = hashtag;
        }
        public static Article of(String title,String content,String hashtag){
                            //new 키워드를 사용하지 않고 객체를 만들 수 있도록 한다.
                return new Article(title,content,hashtag);
        }

        @Override                                       //id != null 영속화가 되면 통과
        public boolean equals(Object o) {       //영속화 되지 않은 객체는 탈락시키게 하려고 이렇게 만듬
                if (this == o) return true;
                if (!(o instanceof Article article)) return false;
                return id != null && id.equals(article.id);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id);
        }
}
