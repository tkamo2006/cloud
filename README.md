<h1>
구르미 월드
</h1>

![구르미월드_소개이미지](https://github.com/baejb/cloudworld/assets/82064490/5f4d1094-ba9f-494d-840f-783f5020ab37)


<p align="center">
  나만의 홈페이지를 공유해보세요 ! <br>
  친구들이 내 홈페이지를 방문하거나 방명록을 작성해준다면, 나의 구르미는 멋지게 성장할 수 있습니다!  <br>
</p>
<hr>
<h2>프로젝트 소개</h2>
<ol>
  <li>구르미월드는 네가지 종류의 1,2,3레벨 구르미가 존재합니다. </li>
  <li>구르미월드는 공유를 통해 자신의 홈페이지를 전달하고, 다른 유저가 나의 홈페이지에 방문하거나, 방명록을 작성해주면 포인트를 획득할 수 있습니다. <br>
    자신이 직접 포인트를 올리는 방법은 존재하지 않습니다. </li>
  <li>방명록은 일반 방명록과 비밀 방명록으로 나뉩니다. 비밀 방명록은 작성자와 홈페이지 주인만 확인 할 수 있습니다. 다른 유저에겐 비밀글이 작성되었다는 사실만 확인 가능합니다.</li>
  <li>소셜로그인으로 구현하여 손쉽게 로그인하여 서비스를 이용할 수 있습니다.</li>
  <li>팔로우 기능이 존재합니다.팔로우 목록은 자신만 확인할 수 있습니다.</li>
  
</ol>
<hr>
<h2>팀원 소개 및 역할</h2>
<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/kchaeys2"><img src="https://avatars.githubusercontent.com/u/82064490?v=4" width="150px;" alt=""/><br /><b>배정빈</b></a><br /></td>
      <td align="center"><a href="https://github.com/tkamo2006"><img src="https://avatars.githubusercontent.com/u/101330766?v=4" width="150px;" alt=""/><br /><b>한유성</b></a><br /></td>
</a><br /></td>
     <tr/>
      <td align="center">FE</td>
      <td align="center">BE</td>
    </tr>
    <tr>
     <td>
       UI - 랜딩페이지, 로그인 모달, 홈 페이지, 방명록 페이지, 친구목록 페이지,성장 페이지, 정보 수정 페이지 <br>

기능 - 소셜 로그인(카카오, 구글),자동 로그인, 방명록CRUD, <br>방명록 댓글 CRUD, 방명록 비밀글, 유저 친구 등록,삭제, 방명록 페이지네이션, 링크 공유
<br>
구르미 캐릭터 자체 제작 ,랜딩 페이지 제작</td>
      <td>Security, JWT, OAuth2 카카오 및 구글 로그인

회원 CRUD, 방명록 CRUD, 댓글 CRUD, 유저 팔로우 기능, 방명록 페이지네이션, 구르미 성장하기</td>
      </tr>
  
  </tbody>
</table> 

<hr>
<h2>구현 기능</h2>
<ol>
  <li>
    회원 가입 - 로그인 기능 
  <p>토큰 방식으로 가입을 구현하였고,Kakao, Google OAuth 방식으로 가입 및 로그인할 수 있습니다. </p>
  </li>
  <li>
    유저 팔로우 기능
  </li>
  <p>
    나를 제외한 다른 사람을 팔로우하여,
    팔로우한 사람의 홈으로 이동할 수 있습니다. 
    팔로우한 유저는 친구목록 페이지에서 확인 할 수 있습니다.
  </p>
  <li>방명록 CRUD, 댓글 CRUD </li>
  <p>방명록과 댓글 모두 생성, 조회, 수정, 삭제 기능을 구현하였습니다.
  <br>비밀 방명록을 작성할 수 있습니다.
  </p>
  <li>방명록 페이지네이션 기능</li>
  <p>한 페이지 당 5개의 게시글이 보이게 설정하였고,
방명록은 최신순으로 조회되게 하였습니다.</p>

<li>구르미 성장 하기 기능 </li>

<p>

1Lv : 물방울에서 시작한다. <br>
2Lv : 잼민 구르미 - 최소 포인트 100 <br>
3Lv : 어른 구르미 - 최소 포인트 300 <br>
<br>
포인트 규칙 <br>
- 방명록 개수당 + 3 <br>
- 투데이 개수당 + 1 <br>
- 본인과 비회원은 투데이가 오르지 않는다. 
</p>
  
</ol>


<hr>
<h2>구현 화면 </h2>
<h4>1. 랜딩 페이지 </h4> 
<img width="318" height="700" alt="구르미월드_랜딩페이지" src="https://github.com/baejb/cloudworld/assets/82064490/9a6fc06d-f217-45f8-a462-1ca9eb3099b5">
<h4>2. 로그인 페이지</h4>
<img width="318" height="700" alt="구르미월드_로그인" src="https://github.com/baejb/cloudworld/assets/82064490/6d1e82e1-16a4-4165-af85-4dff92fb1038">
<h4>3. 홈 페이지 </h4>
<img width="318" height="700" alt="구르미월드_홈페이지(나)" src="https://github.com/baejb/cloudworld/assets/82064490/783f2616-a453-4fba-86b2-07d7b036e892"> <br> 
- 내 홈페이지 <br> 

<img width="318" height="700" alt="구르미월드_홈페이지(친구)" src="https://github.com/baejb/cloudworld/assets/82064490/e8a14f7c-5fa7-4df0-945b-2b1f70e23036"> <br> 
-친구 홈페이지 <br> 
<h4>4. 정보 수정 페이지</h4>
<img width="318" height="700" alt="구르미월드_정보수정페이지" src="https://github.com/baejb/cloudworld/assets/82064490/b675a7e6-0a56-4225-a50c-bdd381a63633">
<h4>5. 성장 페이지 </h4>
<img width="318" height="700" alt="구르미월드_성장페이지(성장실패)" src="https://github.com/baejb/cloudworld/assets/82064490/c0563898-2a74-4460-88f6-0168f78724e4">
<img width="318" height="700" alt="구르미월드_성장페이지(모든성장완료)" src="https://github.com/baejb/cloudworld/assets/82064490/28c60452-46c6-456e-8581-f011d61911e3">
<h4>6. 공유하기</h4>
<img width="318" height="700" alt="구르미월드_공유하기" src="https://github.com/baejb/cloudworld/assets/82064490/8cfa39c6-b643-4b18-82dc-c21b6d2314b7">

<h4>7. 친구 목록 페이지</h4>
<img width="318" height="700" alt="구르미월드_친구목록페이지" src="https://github.com/baejb/cloudworld/assets/82064490/71ba199b-5429-4fe0-b5a5-15c207f4ac53">
<h4>8. 방명록 페이지</h4>
<img width="318" height="700" alt="구르미월드_방명록페이지(글없을때)" src="https://github.com/baejb/cloudworld/assets/82064490/b4202dc9-c05b-44dc-85d9-dbfdc5bb42ba"> <br> 
-방명록 글 없을 때  <br> 
<img width="318" height="700" alt="구르미월드_방명록페이지(나)" src="https://github.com/baejb/cloudworld/assets/82064490/2b86b835-ccc8-4288-b1dd-a49cf225d996"> <br> 
-내 방명록 <br> 
<img width="318" height="700" alt="구르미월드_방명록페이지(친구)" src="https://github.com/baejb/cloudworld/assets/82064490/f44c4434-07b8-4277-9651-2674a9943b72"><br> 
-친구 방명록<br> 
<hr>
<h2>기술 스택</h2>
<h3>Front-End</h3>

|   HTML    |    CSS    | JavaScript  | React     | StyledComponents | 
| :-------: | :-------: | :--------: | :--------: | :-------: |
| <img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/html.svg width=60px height=60px>  | <img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/css.svg width=60px height=60px> | <img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/js2.svg width=60px height=60px>  |<img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/react.svg width=60px height=60px> |<img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/styledcomponents.svg width=60px height=60px>| 

|   Vite    |    Jwt    | GoogleOauth | Axios | Vercel | Figma |
| :-------: | :-------: | :--------: | :------: | :----------: |  :-------------: |
|<img src=https://github.com/codestates-seb/seb44_main_034/raw/README/client/public/vite.svg width=80px height=60px>  | <img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/jwtimg.png width=60px height=60px> | <img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/Oauth_logo.svg width =60 height =60> | <img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/axios.svg width=60px height=60px>|<img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/vercel1868.jpeg width=70px height=80px>|<img src="https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/figma.png" width ="48" height ="50"> 


<h3>Back-End</h3>

|  JAVA   |SpringBoot| SpringSecurity |  JPA   |   MYSQL   |  JWT   |
| :-----: | :-------: | :--------: | :------: | :-----: | :-----: | 
| <img src=https://github.com/codestates-seb/seb44_main_034/raw/README/images/stack/java.svg width=60px height=60px> |<img src="https://github.com/codestates-seb/seb44_main_034/assets/111395967/48fb4b1c-16db-43f5-9d72-1ac2ff7a640d" width ="80" height ="50">|<img src="https://github.com/codestates-seb/seb44_main_034/assets/111395967/3183de14-78c3-41ea-8812-f9cfc1965ece" width ="80" height ="50"> |<img src="https://github.com/codestates-seb/seb44_main_034/assets/111395967/78414aad-c1af-4bcb-a2f7-8f6703c87b96" width ="80" height ="50">  |<img src="https://github.com/codestates-seb/seb44_main_034/assets/111395967/e92edcd5-6dca-4e63-b006-2b1b7973b2dc" width ="80" height ="50"> |<img src="https://github.com/codestates-seb/seb44_main_034/assets/111395967/80fe6786-8c00-4080-890d-b2a207dc5889" width ="80" height ="50">  |

<hr>

<h2>문서</h2>
피그마-https://www.figma.com/file/JMXPsFOM3XyRuNXjFtvVs8/%ED%99%94%EB%A9%B4%EC%84%A4%EA%B3%84?type=design&node-id=0-1&mode=design&t=Pdz8x0ruISR3sEBr-0 <br>
노션-https://www.notion.so/ee645c37e8ee4c86873a1cb2f79c021a  <br>
ERD-https://www.erdcloud.com/d/ScRWQiPotaiFkJhbZ  <br>
API-https://diagnostic-freedom-600.notion.site/Cloud-API-fe365262b6224ee2807b508a06906e39  <br>
