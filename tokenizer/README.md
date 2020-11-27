# yido-tokenizer
- jdk 1.8
-   mecab
    -   mecab-0.996-ko-0.9.2
    -   mecab-ko-dic-2.1.1-20180720
    -   mecab-java-0.996  

# 문서
- tokenizer 문서 및 연구 내역
    - https://wigoai.atlassian.net/wiki/spaces/WP/pages/291635238/tokenizer
    

# 사용법
- 0.1 버젼은 내부적으로 mecab을 사용하기때문에 사전관리는 mecab 사전관리 방법에 따름니다
    - https://github.com/Pusnow/mecab-ko-dic-msvc/tree/master/user-dic

- mecab 테스트 결과 프로세스가 실행중 사전 교체는 안됩니다.
    - 프로세스 restart 해야만 적용 됩니다
    - 관련 방법이 있으면 공유 부탁 드립니다.

```java
import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.TokenizerManager;
import org.moara.yido.tokenizer.word.CompoundToken;
import org.moara.yido.tokenizer.word.WordToken;

public class TokenizerExample {
    public static void main(String[] args) {
        Token[] tokens = TokenizerManager.getInstance().getTokenizer().getTokens("시내버스가 위고에 다녀요");
        for(Token token : tokens){
            WordToken wordToken = (WordToken)token;

            System.out.print(token.getText() +", " + wordToken.getPartOfSpeech());

            if(wordToken instanceof CompoundToken){
                CompoundToken compoundToken =(CompoundToken) wordToken;
                String [] wordIds = compoundToken.getWordIds();
                for (String wordId : wordIds) {
                    System.out.print(" " + wordId);
                }
            }
            System.out.println();
        }
    }
}
```
# gradle
implementation 'org.moara.yido:tokenizer:0.1.1'
- etc 
    - https://mvnrepository.com/artifact/org.moara.yido/tokenizer/0.1.1

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
    
