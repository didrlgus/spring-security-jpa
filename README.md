# Spring boot Security 와 JPA를 연동한 인증처리 연습



## 인증처리 과정 (DB 데이터를 이용한 방법)
![image](https://user-images.githubusercontent.com/40568894/68768395-c4b6cf80-0665-11ea-8d79-fabb2f38e9a6.png)
 
1. 클라이언트는 id와 password 값을 입력하여 서버로 인증 요청을 보낸다.
2. 스프링 시큐리티는 여러개의 필터들의 체인으로 구성되어있는데 그 중 인증필터(UserNamePasswordAuthenticationFilter)에서 클라이언트로부터 받은 id와 password가 담긴 Authentication 객체를 생성한다.
3. AuthenticationManger는 AuthenticationProvider로 AuthenticationProvider는 DaoAuthenticationProvider로 Authentication 객체를 전달한다. (DB 데이터를 이용한 Credential 인증 방식일 경우)
4. DaoAuthenticationProvider는 Authentication에 담긴 id값을 매개변수로 하여 UserDetailsService 인터페이스를 구현한 클래스에서 오버라이딩한 loadUserByUserName() 메서드를 호출한다.
5. loadUserByUserName() 메서드는 매개변수로 전달받은 id값을 이용하여 DB에 접근하고 DB에 저장된 유저 정보를 얻는다. (만약 유저정보가 DB에 없다면 직접 Exception 발생 시키도록 개발)
6. DB로부터 얻어온 유저정보(id, password, authorities)를 이용하여 스프링 시큐리티에서 지원하는 UserDetails(인증 과정에 필요할법한 정보들을 추상화한 인터페이스) 객체를 만들어 반환한다.
7. DaoAuthenticationProvider는 반환받은 UserDetails 객체의 비밀번호와 기존의 Authentication 객체에 있던 비밀번호를 비교하여 같다면 인증을 성공시키고, 다르면 인증을 실패시킴(Bad Credentials 예외 발생)
8. 인증이 성공하면 Authentication 객체에 유저의 principal 정보가 담긴다.
9. Authentication 객체는 SecurityContext 내에 저장되어 관리되고 SecurityContextHolder 내에서 SecurityContext가 관리된다. SecurityContextHolder는 기본적으로 *ThreadLocal에 의해 관리된다.
10. 또한, 인증처리가 성공하면 SPRING_SECURITY_CONTEXT 라는 속성으로 세션에 인증 정보가 추가된다. 
11. 인증 성공 시 SuccessHandler가 호출되어 성공 로직을 작성할 수 있으며, 인증 실패 시 FailureHandler가 호출되어 실패 로직을 작성할 수 있다.
12. 만약 다음 요청이 인증처리가 요구되는 요청이라면 먼저 세션에 인증정보가 있는지 확인하고 있다면 인증처리 로직을 생략, 없다면 인증처리 로직 수행하는 방식으로 동작함