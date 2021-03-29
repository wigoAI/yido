# Splitter
한글 문서 데이터를 문단 또는 문장 단위로 구분해주는 구분기

# Usage
## Get Splitter
모든 구분기는 단 한개만 생성되며 키값과 함께 SplitterManager내부의 HashMap 에서 관리된다.

### 기본 구분기
#### 기본 메서드로 가져오기
```java
class Main{
    public static void main(String[] args){
        Splitter basicSplitter =  SplitterManager.getInstance().getSplitter();
    }
}
        
```


#### 키 값으로 가져오기
```java
class Main{
    public static void main(String[] args) {
        Splitter splitter = SplitterManager.getInstance().getSplitter("test");
    }
}
```


### 구분기 생성
구분기의 속성값은 json형태로 관리되며 /data/splitter 경로에  .json파일로 저장하면 SplitterManager에서 읽어올 수 있다.

#### JSON file
```json
//test.json
{
  "id": "test",
  "name": "test",
  "minimum_split_length": 5,
  "contain_split_condition": "Y",
  "conditions": ["test"],
  "exceptions": ["bracket_exception"]
}
```
#### Code
```java
class Main{
    public static void main(String[] args) {
        Splitter splitter = SplitterManager.getInstance().getSplitter("test");
    }
}
```
SplitterManager는 내부적으로 매개변수로 받아오는 splitter id의 인스턴스가 존재하지 않으면 해당 id를 통해 json을 읽어와 splitter를 생성한다.

# Version
## Java
1.8

# Data
구분기에 사용되는 사전 데이터는 `dic`디렉터리 내부에 존재하지만, 이후 업데이트 여부에 따라 변경되거나 제거될 수 있다.

# gradle
implementation 'org.moara.yido:splitter:0.1.0'
- etc
    - https://mvnrepository.com/artifact/org.moara.yido/splitter/0.1.0
