# yido 
- yido -> 세종대왕 본명
- 한국어 자연어 처리 프로젝트
- jdk 1.8
- 상세 문서 페이지
    - https://wigoai.atlassian.net/wiki/spaces/WP/pages/291635219/yido
    
# modules
## splitter (문단 문장 구분기)
- implementation 'org.moara.yido:splitter:0.1.0'
- 문단구분과 문장구분등에 사용 됨
- 구분기는 이전 룰베이스 방식의 구분기가 성능이 좋은것을 확인함 
    - 구분 데이터 정확도와 구분속도 등 모두 우수한 성능을 나타냄
    - 이전 방식을 관리방안이 쉽게 더 잘구성하여 관리하는 프로젝트로 진행함
    - 이전에는 문장 구분기 였는데 구분기로 잡아서 문단 구분에도 활용할 수 있게 함

## tokenizer
 - implementation 'org.moara.yido:tokenizer:0.1.2'

### 문서 및 연구 내역
 - https://wigoai.atlassian.net/wiki/spaces/WP/pages/291635238/tokenizer
### 모듈 설명 및 소스
 - https://github.com/wigoAI/yido/tree/master/tokenizer 

## text-mining
 - implementation 'org.moara.yido:text-mining:0.1.0'
 - text mining 방법의 클래스 제공

## 분류 평가 (이항분류, 다항분류)
- implementation 'org.moara.yido:evaluation:0.1.0'

## 개체명 인식
 - implementation 'org.moara.yido:ner:0.1.0'

## 신조어

## 오탈자교정

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
