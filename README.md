# 뉴스피드 개인 프로젝트 👍

내일배움캠프 Spring 5기 트랙에서 진행한 Newsfeed 개인 프로젝트에 관한 README.md 문서입니다.

### [ 프로젝트 소개 ]

#### 총 개발 기간(약 8일)
2025.03.07 ~ 2025.03.15


-----

### [ API 동작 캡쳐본 ]


#### 1. 프로필 관리

- **프로필 조회 기능** - 다른 사용자의 프로필 조회 시, 민감한 정보는 표시되지 않습니다.

**[ 본인의 프로필 조회 ]**
  
<img width="726" alt="스크린샷 2025-03-16 오전 11 33 57" src="https://github.com/user-attachments/assets/30d6289f-87fb-457a-bc9b-69e5f8c7576b" />

</br>
</br>
</br>

**[ 다른 유저의 프로필 조회 ]**

<img width="722" alt="스크린샷 2025-03-16 오전 11 34 12" src="https://github.com/user-attachments/assets/6dd695f1-00f8-4728-840a-7baabc29e6cd" />


- **프로필 수정 기능** - 로그인한 사용자는 본인의 사용자 정보를 수정할 수 있습니다.

**[ 본인의 프로필 수정 ]**

<img width="723" alt="스크린샷 2025-03-16 오전 11 41 51" src="https://github.com/user-attachments/assets/839b808e-4711-497b-ad02-6fb6ebad8eab" />

</br>
</br>
</br>

**[ 다른 유저의 프로필 수정 ]**

<img width="726" alt="스크린샷 2025-03-16 오전 11 42 19" src="https://github.com/user-attachments/assets/f1dfd79b-aea3-4153-a81e-b88220f617d0" />

</br>
</br>
</br>

**비밀번호 수정 및 예외처리 기능** - 로그인한 사용자는 본인의 사용자 정보를 수정할 수 있습니다.

**[ 비밀번호 수정 성공 ]**

<img width="724" alt="스크린샷 2025-03-16 오전 11 51 01" src="https://github.com/user-attachments/assets/e1e74474-da40-48a1-a02b-f0d90d320a1a" />

</br>
</br>

**[ 비밀번호 수정 실패 ]**

1. 새로운 비밀번호가 비밀번호 형식에 맞지 않은 경우

<img width="723" alt="스크린샷 2025-03-16 오전 11 49 57" src="https://github.com/user-attachments/assets/44180e02-2a52-4e97-91b9-2175f8e1f720" />

</br>
</br>

2. 새로운 비밀번호가 기존의 비밀번호와 같은 경우

<img width="721" alt="스크린샷 2025-03-16 오전 11 49 38" src="https://github.com/user-attachments/assets/f728edce-9b15-47c4-bf3c-bd718fe6a8f0" />

</br>
</br>

3. 기존의 비밀번호가 일치하지 않는 경우

<img width="723" alt="스크린샷 2025-03-16 오전 11 50 39" src="https://github.com/user-attachments/assets/42776a46-f1cd-4cdc-8012-2e9894981ffb" />


---


#### 2. 뉴스피드 게시물 관리

**[ 게시글 생성 ]**

<img width="724" alt="게시글 생성" src="https://github.com/user-attachments/assets/57e3f4cb-0e99-4171-9b4c-cb20ed6e16ad" />

</br>
</br>

**[ 게시글 전체 조회 ]**

<img width="719" alt="게시글 전체 조회" src="https://github.com/user-attachments/assets/fd8ddb1b-4de9-41cc-a244-81bd688b45ec" />

</br>
</br>

**[ 게시글 단건 조회 ]**

<img width="724" alt="게시글 단건 조회" src="https://github.com/user-attachments/assets/ac0dc8fd-a3d6-4db9-82da-7226c19b36f0" />

</br>
</br>

**[ 팔로우한 유저의 게시글 조회 ]**

<img width="717" alt="팔로워 게시글 조회" src="https://github.com/user-attachments/assets/3400c861-43c4-4465-be62-a3aab3a0772b" />

</br>
</br>

**[ 게시글 수정 ]**

<img width="722" alt="게시글 수정" src="https://github.com/user-attachments/assets/08e820de-caae-4650-bfe9-9ebfc4674582" />

</br>
</br>

**[ 게시글 삭제 ]**

<img width="718" alt="게시글 삭제" src="https://github.com/user-attachments/assets/46dfa709-d9d0-4ce0-bb31-f50e58539324" />

</br>
</br>

**[ 게시글 삭제 후 조회 ]**

<img width="722" alt="게시글 삭제 후 조회" src="https://github.com/user-attachments/assets/bbf35070-ee21-4ab9-b439-9f4798a0570c" />


</br>
</br>
</br>

**[ 다른 유저의 게시글 수정 시도 ]**

<img width="723" alt="스크린샷 2025-03-16 오후 12 52 13" src="https://github.com/user-attachments/assets/1630f3ae-050e-461a-8941-555ab840d0ee" />


</br>
</br>
</br>

**[ 다른 유저의 게시글 삭제 시도 ]**

<img width="721" alt="스크린샷 2025-03-16 오후 12 52 24" src="https://github.com/user-attachments/assets/ae27eb61-da1a-4941-82d2-7279ec95e8e5" />


---

#### 3. 사용자 인증

**회원 가입**

- 첫 번째 유저

<img width="724" alt="스크린샷 2025-03-16 오전 11 28 36" src="https://github.com/user-attachments/assets/01938ac2-56d4-4f21-bef2-e6c009909787" />

</br>
</br>
</br>

- 두 번째 유저

<img width="722" alt="스크린샷 2025-03-16 오전 11 30 14" src="https://github.com/user-attachments/assets/0c193054-1773-4d9e-b3b0-b0b0cbd0878f" />

</br>
</br>
</br>

**로그인**

- 첫 번째 유저로 로그인

<img width="723" alt="스크린샷 2025-03-16 오전 11 29 24" src="https://github.com/user-attachments/assets/4a63b37d-2571-47e4-8380-06f649ba0a83" />

</br>
</br>
</br>

**회원 가입 및 로그인 실패**

1. 중복된 사용자 아이디로 가입

<img width="724" alt="스크린샷 2025-03-16 오후 12 58 13" src="https://github.com/user-attachments/assets/e3da9fee-a004-48e9-862f-bce36835d5cd" />

</br>
</br>
</br>

2. 사용자 아이디나 비밀번호가 형식에 맞지 않는 경우

<img width="718" alt="스크린샷 2025-03-16 오후 12 58 46" src="https://github.com/user-attachments/assets/8ef225fa-9d55-4f58-b38a-e6048f31d0bd" />

--- 

#### 4. 팔로우 관리

**[ 팔로우 성공 ]**

<img width="720" alt="팔로우 성공" src="https://github.com/user-attachments/assets/0cbcd799-9411-4178-a807-168649f89e2f" />

</br>
</br>

**[ 팔로우 후 팔로잉 및 팔로워 수 변경 ]**

- 팔로우 요청 보낸 유저

<img width="722" alt="스크린샷 2025-03-16 오후 1 09 24" src="https://github.com/user-attachments/assets/f7e33da6-2227-4eb1-a17d-c2313269e47e" />

</br>
</br>

- 팔로우 요청 받은 유저

<img width="714" alt="스크린샷 2025-03-16 오후 1 09 13" src="https://github.com/user-attachments/assets/bb8705d8-fe5e-4b66-9ac0-5c16b8622d39" />

</br>
</br>

**[ 이미 팔로우한 유저를 다시 팔로우 - 실패 ]**

<img width="723" alt="스크린샷 2025-03-16 오후 1 13 21" src="https://github.com/user-attachments/assets/26b74d9f-50f6-4769-ae83-dc705997f539" />

</br>
</br>

**[ 언팔로우 성공 ]**

<img width="722" alt="언팔로우 성공" src="https://github.com/user-attachments/assets/d7d24cc6-f01a-499c-a47a-4e78f65bdc12" />

</br>
</br>

**[ 언팔로우 후 팔로잉 및 팔로워 수 변경 ]**

- 언팔로우 한 유저

<img width="719" alt="스크린샷 2025-03-16 오후 1 14 54" src="https://github.com/user-attachments/assets/d89b57fd-3364-4e32-a462-1cee1c6787f0" />


</br>
</br>

- 언팔로우 당한 유저

<img width="721" alt="스크린샷 2025-03-16 오후 1 15 04" src="https://github.com/user-attachments/assets/e232aa3b-a369-4459-9a14-2fd4bc2197bc" />



</br>
</br>

**[ 이미 언팔로우한 유저를 다시 언팔로우 - 실패  ]**

<img width="725" alt="언팔로우 후 다시 언팔로우 실패" src="https://github.com/user-attachments/assets/a5b50c6c-6c9a-4f6a-b0eb-c66be9b77df9" />


--- 

#### 5. 검색 및 정렬 기능

**[ 게시글 날짜별 조회 ]**

<img width="722" alt="게시글 날짜별 조회" src="https://github.com/user-attachments/assets/c7f2d0b7-5759-4119-9d7f-0e295ed890d3" />

</br>
</br>

**[ 게시글 수정일자 내림차순 정렬 ]**
<img width="722" alt="게시글 삭제 후 조회" src="https://github.com/user-attachments/assets/bbf35070-ee21-4ab9-b439-9f4798a0570c" />

--- 

#### 6. 댓글 기능

**[ 댓글 생성 ]**

<img width="721" alt="댓글 생성" src="https://github.com/user-attachments/assets/1b0ad7d9-0eda-4714-96b9-5f66c06b7c95" />

</br>
</br>

**[ 댓글 조회 ]**

<img width="711" alt="댓글 전체 조회" src="https://github.com/user-attachments/assets/d887b2d3-ead6-4e13-9243-6078d6df2b79" />

</br>
</br>

**[ 댓글 수정 ]**

<img width="721" alt="댓글 수정" src="https://github.com/user-attachments/assets/af35d70c-cb3b-43e2-a592-28ab90be1431" />

</br>
</br>

**[ 댓글 삭제 ]**

<img width="722" alt="댓글 삭제" src="https://github.com/user-attachments/assets/de108558-8448-4ccb-80b0-15da53bbcff3" />

--- 

#### 7. 좋아요 기능























