# NER
## Named Entity Recognizer
사용자는 원하는 인식기의 id를 통해 NamedEntityRecognizerManager로부터 NamedEntityRecognizer의 인스턴스를 얻을 수 있다.  
  
이 둘은 모두 추상체로써 큰 범주마다 구현체가 존재한다.  
  
예를 들어 사람 개체명 범주에는NamedEntityRecognizerManager의 구현체인 PersonNamedEntityRecognizerManager가 존재하며 여기서 기자 개체명을 인식할 수 있는 ReporterRecognizer의 인스턴스를 받아 사용할 수 있다.  

```java
NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("PS_REPORTER");

NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);
```

# 개체명 태그
개체명 인식 대상으로 분류될 수 있는 개체명 태그는 ETRI(한국전자통신연구원)의 태그세트 분류 체계를 참고함

## 태그명 예시
|대분류|세분류|정의|
|---|---|---|
|PERSON(PS)|PS_REPORTER|기자명|
|LOCATION(LC)|LC_TOUR|관광명소|
|ORGANIZATION(OG)|OGG_ART|예술 기관/단체|
|DATE(DT)|DT_DAY|날짜/절기|
|...|...|...|
|PLANT(PT)|PT_TREE|나무 이름|


# gradle
implementation 'org.moara.yido:ner:0.1.0'
- etc
    - https://mvnrepository.com/artifact/org.moara.yido/ner/0.1.0


# Version
## Java
1.8


