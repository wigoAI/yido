# yido-tokenizer
- jdk 1.8
-   mecab
    -   mecab-0.996-ko-0.9.2
    -   mecab-ko-dic-2.1.1-20180720
    -   mecab-java-0.996  

# 문서
- tokenizer 문서 및 연구 내역
    - https://wigoai.atlassian.net/wiki/spaces/WP/pages/291635238/tokenizer
- 사용 가이드
    - https://wigoai.atlassian.net/wiki/spaces/WP/pages/307003403/tokenizer    
- 설정 가이드
    - https://wigoai.atlassian.net/wiki/spaces/WP/pages/306937886/tokenizer
# 사용법
- 0.1 버젼은 내부적으로 mecab을 사용하기때문에 사전관리는 mecab 사전관리 방법에 따름니다
    - https://github.com/Pusnow/mecab-ko-dic-msvc/tree/master/user-dic
    - mecab의 성능이 좋고 많은곳에서 사용되고 있기 때문에 기존에 개발된 tokenizer 보다 mecab을 사용하는게 좋을 수 있어서 이후개발은 mecab을 연동하는 식으로 진행될 가능성도 높습니다. 
- mecab 테스트 결과 프로세스가 실행중 사전 교체는 안됩니다.
    - 프로세스 restart 해야만 적용 됩니다
    - 관련 방법이 있으면 공유 부탁 드립니다.

- 자세한 사용법은 위에있는 문서페이를 참조해 주세요.

multi tokenizer 기능에 업데이트 되었습니다. multi tokeziner 에서 maven central 에 올라가 있지 않은 프로젝트 들로 인해서 버전업 작업은 진행하지 못하였습니다 

https://github.com/wigoAI/yido/tree/master/service-modules/tokenizer-rest

구현내용은 위 경로 참고 바랍니다.

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

# 품사
품사 태그표는 세종품사테그를 표준으로 정하고 mecab 에서 쓰이는 품사중 더 세분화한 부분이 있을 경우 세분화 한다

- NNG	일반 명사
- NNP	고유 명사
- NNB	의존명사
- NNBC	단위를 나타내는 명사 (mecab)
- NR	수사
- NP	대명사
- VV	동사
- VA	형용사
- VX	보조 용언
- VCP	긍정 지정사
- VCN	부정 지정사
- MM	관형사
- MAG	일반 부사
- MAJ	접속 부사
- IC	감탄사
- JKS	주격 조사
- JKC	보격 조사
- JKG	관형격 조사
- JKO	목적격 조사
- JKB	부사격 조사
- JKV	호격 조사
- JKQ	인용격 조사
- JX	보조사
- JC	접속 조사
- EP	선어말 어미
- EF	종결 어미
- EC	연결 어미
- ETN	명사형 전성 어미
- ETM	관형형 전성 어미
- XPN	체언 접두사
- XSN	명사 파생 접미사
- XSV	동사 파생 접미사
- XSA	형용사 파생 접미사
- XR	어근
- SF	마침표, 물음표, 느낌표
- SE	줄임표
- SS	따옴표,괄호표,줄표
- SSO  여는 괄호 (, [ (mecab)
- SSC  닫는 괄호 ), ] (mecab)
- SP	쉼표,가운뎃점,콜론,빗금
- SO	붙임표(물결,숨김,빠짐)
- SW	기타기호 (논리수학기호,화폐기호)
- SL	외국어
- SH	한자
- SN	숫자

# gradle
implementation 'org.moara.yido:tokenizer:0.1.2'
- etc 
    - https://mvnrepository.com/artifact/org.moara.yido/tokenizer/0.1.2

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
    
