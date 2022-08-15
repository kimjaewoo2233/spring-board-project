package com.example.springproject.dto.security;

import com.example.springproject.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String memo
) implements UserDetails {
        public static BoardPrincipal of(String username,String password,String email, String nickname,String memo){
            Set<RoleType> roleTypes = Set.of(RoleType.USER);    //하나만 들어간 Set
            //넣을 떄는 타입을 바꿔줘야한다 .GrantedAuthority 타입을 상속받은 타입으로 바꿔줘야한다.
            return new BoardPrincipal(
                    username,
                    password,
                    roleTypes.stream()
                            .map(RoleType::getName) //ROLE 타입에 이름을 가지고 온다 (ROLE_USER)을 가져옴옴
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toUnmodifiableSet())
                    ,
                    email,
                    nickname,
                    memo
            ); //처음 인증이 통과되었을때 생성자에 의해서 한번 롤타입이 만들어진다.
        }


        public static BoardPrincipal from(UserAccountDto dto){
            return BoardPrincipal.of(
                    dto.userId(),
                    dto.userPassword(),
                    dto.email(),
                    dto.nickname(),
                    dto.memo()
            );
        }

        public UserAccountDto toDto(){  //이 객체를 DTO객체로 바꾼다.
            return UserAccountDto.of(
                    username,
                    password,
                    email,
                    nickname,
                    memo
            );
        }

        @Override public String getUsername(){return username;}
        @Override public String getPassword(){return password;}
        @Override public Collection<? extends GrantedAuthority> getAuthorities(){return authorities;}


        @Override public boolean isAccountNonExpired(){return true;}
        @Override public boolean isAccountNonLocked(){return true;} //락이 걸렸는지
        @Override public boolean isCredentialsNonExpired(){return true;}    //기한만료
        @Override public boolean isEnabled(){return true;}  //활성화되어있는가

        public enum RoleType{
            USER("ROLE_USER");  //ROLE_ 이 prefix로 붙은 이유는 문자열ㄹ로 권한 체크하는 시큐리티 규칙이다

            @Getter
            private  final String name;

            RoleType(String name){
                this.name = name;
            }
        }
}




