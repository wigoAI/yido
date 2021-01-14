# NER
## Named Entity Recognizer
사용자는 원하는 인식기의 id를 통해 NamedEntityRecognizerManager로부터 NamedEntityRecognizer의 인스턴스를 얻을 수 있다.  
  
이 둘은 모두 추상체로써 큰 범주마다 구현체가 존재한다.  
  
예를 들어 사람 개체명 범주에는NamedEntityRecognizerManager의 구현체인 PersonNamedEntityRecognizerManager가 존재하며 여기서 기자 개체명을 인식할 수 있는 ReporterRecognizer의 인스턴스를 받아 사용할 수 있다.  

```java
NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");

NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);
```



# Version
## Java
1.8


