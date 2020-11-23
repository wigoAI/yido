# yido 
- yido -> 세종대왕 본명
- 한국어 자연어 처리 프로젝트
- jdk 1.8
- 상세 문서 페이지
    - https://wigoai.atlassian.net/wiki/spaces/WP/pages/291635219/yido
    
# modules
## splitter (문단 문장 구분기)
- 문단구분과 문장구분등에 사용 됨
- 구분기는 이전 룰베이스 방식의 구분기가 성능이 좋은것을 확인함 
    - 구분 데이터 정확도와 구분속도 등 모두 우수한 성능을 나타냄
    - 이전 방식을 관리방안이 쉽게 더 잘구성하여 관리하는 프로젝트로 진행함
    - 이전에는 문장 구분기 였는데 구분기로 잡아서 문단 구분에도 활용할 수 있게 함

## tokenizer
### 토큰 구분
 - 여러 테스트를 해본결과 mecab 이 가장 좋음
 - 속도 + 분석정확도
 - multi thread 를 지원하지 않는것 같고 inmemory 방식으로 운영할때 사전의 변경정보가 반영이 쉬운지 는 테스트하지 못함
 - multi thread 를 지원하지 않아도 다른 토크나이져보다 성능이 빠름 (10배이상 차이)
 - 이전버전인 moara 토크나이저는 경량화하면 mecab보다 2배정도 차이가 나고(mecab 이 2배 빠름, 더빠르게 제작 가능함) multi thread 를 지원 함
 - 단 품질을 관리하기 어려워 우선은 mecab을 활용하는 방향으로 함
 - 공통된 형태로 제작하여 초기에는 mecab을 활용하고 더 좋은게 나오면 변경
 - mecab을 사용하다 발견된 문제들은 이전에 단어 추출기를 만들었던 노하우를 바탕으로 성능 좋은 한국어 토크나이저를 개발하여 상황에 맞는 것을 사용하게 지원
 - 이전 단어 추출기에서 지원했던 한글형 숫자 인식/ 코드인식 / 개인정보 인식등은 따로 만들어서 하이브리드 형태로 활용
 - 관련 프로젝트는 유사도, 분류, 챗봇 등에 연관성이 높은 프로젝트 이므로 계속 좋은 방안으로 발전될 예정임 

### 신조어

### 오탈자교정


## 전처리기
- 전반각
- 개인정보 처리 (삭제, 블라인드)
- 특수문자 변환

### STT 한글형 기호
- 날짜 ( 7일전 1시간 등, 한글형 날짜)
- 한글형 금액
- 한글형 자동차번호


# communication
### blog, homepage
 - https://wigo.ai/
 - https://wigoai.github.io/
 
### slack
 - moaara.slack.com
 - wigoailab.slack.com


# main developer
 - macle
    -  [github.com/macle86](https://github.com/macle86)
    
 - wjrmffldrhrl
    - [github.com/wjrmffldrhrl](https://github.com/wjrmffldrhrl)