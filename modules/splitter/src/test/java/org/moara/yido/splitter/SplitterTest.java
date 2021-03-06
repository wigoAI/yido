package org.moara.yido.splitter;

import com.seomse.commons.data.BeginEnd;
import org.junit.jupiter.api.*;

import org.moara.yido.splitter.utils.SplitCondition;
import org.moara.yido.splitter.utils.Validation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class  SplitterTest {
    static volatile int[] endCount = {0};
    String[] data = {"강남역 맛집으로 소문난 강남 토끼정에 다녀왔습니다. 회사 동료 분들과 다녀왔는데 분위기도 좋고 음식도 맛있었어요 다만, 강남 토끼정이 강남 쉑쉑버거 골목길로 쭉 올라가야 하는데 다들 쉑쉑버거의 유혹에 넘어갈 뻔 했답니다 강남역 맛집 토끼정의 외부 모습. 강남 토끼정은 4층 건물 독채로 이루어져 있습니다. 역시 토끼정 본 점 답죠?ㅎㅅㅎ 건물은 크지만 간판이 없기 때문에 지나칠 수 있으니 조심하세요 강남 토끼정의 내부 인테리어. 평일 저녁이었지만 강남역 맛집 답게 사람들이 많았어요. 전체적으로 편안하고 아늑한 공간으로 꾸며져 있었습니다ㅎㅎ 한 가지 아쉬웠던 건 조명이 너무 어두워 눈이 침침했던... 저희는 3층에 자리를 잡고 음식을 주문했습니다. 총 5명이서 먹고 싶은 음식 하나씩 골라 다양하게 주문했어요 첫 번째 준비된 메뉴는 토끼정 고로케와 깻잎 불고기 사라다를 듬뿍 올려 먹는 맛있는 밥입니다. 여러가지 메뉴를 한 번에 시키면 준비되는 메뉴부터 가져다 주더라구요. 토끼정 고로케 금방 튀겨져 나와 겉은 바삭하고 속은 촉촉해 맛있었어요! 깻잎 불고기 사라다는 불고기, 양배추, 버섯을 볶아 깻잎을 듬뿍 올리고 우엉 튀김을 곁들여 밥이랑 함께 먹는 메뉴입니다. 사실 전 고기를 안 먹어서 무슨 맛인지 모르겠지만.. 다들 엄청 잘 드셨습니다ㅋㅋ 이건 제가 시킨 촉촉한 고로케와 크림스튜우동. 강남 토끼정에서 먹은 음식 중에 이게 제일 맛있었어요!!! 크림소스를 원래 좋아하기도 하지만, 느끼하지 않게 부드럽고 달달한 스튜와 쫄깃한 우동면이 너무 잘 어울려 계속 손이 가더라구요. 사진을 보니 또 먹고 싶습니다 간사이 풍 연어 지라시입니다. 일본 간사이 지방에서 많이 먹는 떠먹는 초밥(지라시스시)이라고 하네요. 밑에 와사비 마요밥 위에 연어들이 담겨져 있어 코끝이 찡할 수 있다고 적혀 있는데, 난 와사비 맛 1도 모르겠던데...? 와사비를 안 좋아하는 저는 불행인지 다행인지 연어 지라시를 매우 맛있게 먹었습니다ㅋㅋㅋ 다음 메뉴는 달짝지근한 숯불 갈비 덮밥입니다! 간장 양념에 구운 숯불 갈비에 양파, 깻잎, 달걀 반숙을 터트려 비벼 먹으면 그 맛이 크.. (물론 전 안 먹었지만...다른 분들이 그렇다고 하더라구요ㅋㅋㅋㅋㅋㅋㅋ) 마지막 메인 메뉴 양송이 크림수프와 숯불떡갈비 밥입니다. 크림리조또를 베이스로 위에 그루통과 숯불로 구운 떡갈비가 올라가 있어요! 크림스튜 우동 만큼이나 대박 맛있습니다...ㅠㅠㅠㅠㅠㅠ (크림 소스면 다 좋아하는 거 절대 아닙니다ㅋㅋㅋㅋㅋㅋ) 강남 토끼정 요리는 다 맛있지만 크림소스 요리를 참 잘하는 거 같네요 요건 물만 마시기 아쉬워 시킨 뉴자몽과 밀키소다 딸기통통! 유자와 자몽의 맛을 함께 느낄 수 있는 뉴자몽은 상큼함 그 자체였어요. 하치만 저는 딸기통통 밀키소다가 더 맛있었습니다ㅎㅎ 밀키소다는 토끼정에서만 만나볼 수 있는 메뉴라고 하니 한 번 드셔보시길 추천할게요!! 강남 토끼정은 강남역 맛집답게 모든 음식들이 대체적으로 맛있었어요! 건물 위치도 강남 대로변에서 조금 떨어져 있어 내부 인테리어처럼 아늑한 느낌도 있었구요ㅎㅎ 기회가 되면 다들 꼭 들러보세요~",
            "오늘은 오빠와 오랜만에 충장로 맛집탐방을 다녀왔습니다~~ 날도 더우니 충장로 냉면 맛집으로 소문난 청년냉면에 가봤습니당!! 지도로 보면 어렵지만, 엔씨웨이브 신호등 건너서 바로 쭉 들어오다 보면 타코야키 맛집있고 조각피자 파는 그 골목으로 들어오다 보면입간판 세워져 있는 곳으로 우회전 해서 들어오면 됩니다!! (사거리 가기 전 좁은골목으로 우회전!) 요기 렌즈미, 점프노래방 사이 골목입니다! 청년냉면 내부 모습입니다~~정말 깔끔하죠??인테리어도 약간 한옥스타일? 대나무도 중간중간 있고~ 넓은 홀에 쇼파좌석도 있고 테이블 좌석도 있었어요! 구시청 맛집 청년냉면 메뉴판입니다~ 물냉,비냉, 코다리물냉,비냉 있구요~ 세트메뉴로 매운치즈등갈비찜+냉면도 있네요^^ (저희는 이걸로 주문!) 그리고 사이드메뉴로 왕만두와 석쇠구이, 대패삼겹도 있었어요!! (왕만두도 하나 추가주문ㅋㅋ) SNS 이벤트도 진행중이였어요^^ 충장로 냉면 맛집 청년냉면은 순수 \"자가제면\"으로 면, 다대기, 육수를 매장에서 직접 만드신대요!! 꿩고기 육수라니~~~~!! 정말 기대되는데요!! 그리고 한가지 더! 바로 테이블마다 무선충전기가 있다는 점^^ (오빠의 S10+는 무선충전이 되는데 제 노트8은 안되더라구요 ㅠㅠ) 주문을 하니 밑반찬이 차려졌습니다! 저희가 제일 좋아하는 흑임자드레싱 샐러드와~ 고추장아찌, 김치, 나물들이 나왔네요!! 매운치즈등갈비찜+냉면 세트와 왕만두가 나왔습니다!!! 만두는 원래 5개 나오는데 한개씩 먹어버렸네요 ㅎㅎ 등갈비찜은 식지않게 고체연료에 불 붙여서 올려주셨구요~~ 매콤해보이는 비쥬얼 최고죠~ㅋㅋ 사이드메뉴인 왕 만두 다섯개!! 크기가 정말 크더라구요 ㅎㅎ 냉면 나오기 전에 먼저 나왔는데 애피타이저로 딱인것같아요!! 꽉찬 속에 피도 얇아서 정말 맛있었어요!! 무조건 추천입니당 ㅎㅎㅎ 냉면에는 만두는 진리쥬~ 그리고 냉면과 함께 먹을 매운치즈등갈비찜!! 다 익혀 나와서 치즈만 녹으면 바로 먹으면 되데요 다른 냉면집에서는 석쇠구이랑 냉면을 같이 먹는데 충장로 냉면 맛집 청년냉면은 매운치즈등갈비찜과의 콜라보!! 매콤한 등갈비 냄새가 너무 좋았어요!! 약간 산장식닭갈비 냄새도 나면서 기분좋은 냄새~ㅎㅎ 양도 꽤 많죠?!! 2인분에 등갈비가 10개? 12개? 잘 기억은 안나지만 꽤 많이 들어있었어요^^ 치즈가 어느정도 녹고 한번 먹어볼까요~ 세상에... 살이 너무너무 부드러워서 젓가락으로 잘 벗겨지더라구요!! 등갈비 먹으면 뼈에 붙은 질긴 살이 항상 먹기 힘들었는데 구시청 맛집 청년냉면의 치즈등갈비찜은 젓가락만 대도 사르르 살이 알아서 떨어지더라구요!! 제가 주문한 비냉!! (육수는 따로 더 달라고 했어요^^) 과연 꿩고기 냉면의 맛은 어떨까요?? 들깨향이 고소하게 느껴지더라구요! 제일 특이한건 면발이였는데, 어느정도 쫄깃한건 좋지만 너무 끈질긴 면들 있잖아요??ㅎㅎ 청년냉면 냉면은 딱 적당히 쫄깃해서 먹기 좋았어요!! 오빠의 물냉!! 살얼음 낀 육수가 정말 시원해보이쥬~~~ 국물 간도 딱 맞아서 그냥 들고 후룩후룩 마셔도 짜지않고 맛있어요!! 역시 물냉면 면도 탱글탱글 너무 질기지 않은!! 꿩고기 육수라 그런지 더 감칠맛이 나더라구요~~ 물냉면 육수 진짜 장난아닙니다...! 계속 마시고 싶어요ㅠㅠ 냉면과도 잘 어울리는데, 그냥 공기밥 시켜서 같이 먹어도 너무 좋겠더라구요!! 저렴한 가격에 냉면과 갈비찜까지 같이 먹을 수 있는게 장점^^ 그리고나서 볶음밥까지 추가 주문해서 먹었습니당^^ 1인분인데도 양이 꽤 많죠ㅎㅎ 고기를 다 뜯어먹고 양념에 비벼도 되고, 한두개 정도 남겨서 고기만 발라서 같이 비벼먹어도 좋겠더라구요!!! 김치도 송송 썰고 김가루 쏵~ 뿌려서 먹는 볶음밥!!! 양념이 맛있으니 볶음밥도 당연히 맛있겠쥬ㅎㅎ 충장로 냉면 맛집 청년냉면!!! 직접뽑은 수제냉면과 꿩고기육수, 맛있는 매운치즈등갈비와 왕만두까지!!! 이제 슬슬 더워지는데, 저렴한 가격으로 시원하고 맛있는 수제냉면 먹으러 꼭 가보시길 추천드립니당~ㅎㅎ",
            "어느 날 부턴가 의문의 목소리가 엘사를 부르고, 평화로운 아렌델 왕국을 위협한다. 트롤은 모든 것은 과거에서 시작되었음을 알려주며 엘사의 힘의 비밀과 진실을 찾아 떠나야한다고 조언한다. 위험에 빠진 아렌델 왕국을 구해야만 하는 엘사와 안나는 숨겨진 과거의 진실을 찾아 크리스토프, 올라프 그리고 스벤과 함께 위험천만한 놀라운 모험을 떠나게 된다. 자신의 힘을 두려워했던 엘사는 이제 이 모험을 헤쳐나가기에 자신의 힘이 충분하다고 믿어야만 하는데... 본편이 너무 잘 완성되면, 그다음 후속편에 대한 기대는 자연히 커질 수 밖에 없다. 그로 인해 제아무리 최선을 다해 만든 후속편이라 해도 달라진 팬들의 눈높이를 채우기란 여간 쉬운 일이 아니다. <겨울왕국 2> 역시 너무 잘만든 전작의 영향 탓인지 이를 넘어서야 한다는 부담감 탓에 아쉬운 문제점들을 대거 드러냈다. 그렇지만 결과적으로 나쁜 후속편은 아니었으며, 부담 없이 볼만한 요소와 재미는 여전해 <겨울왕국>의 열혈팬이라면 충분히 흥미롭게 감상할 것이다. 아렌델 왕국의 기원과 엘사의 마법에 대한 근원을 이야기했다는 점에서 <겨울왕국 2>의 이야기는 매우 야심 찬 방향으로 흘러가게 된다. 이제는 완벽한 가족으로서의 모습을 보여준 엘사, 안나, 크리스토프, 올라프 그리고 스벤의 모습을 보여주며 시종일관 행복한 모습만 보여줄것 같았던 영화는 갑자기 왕국을 덮친 사건을 통해 다시한 번 등장인물들의 새로운 모험을 예고한다. 전체적으로 새로운 모험을 예고하고 있지만, <겨울왕국 2>는 달라진 캐릭터들의 상황과 관계를 중점으로 이야기를 진행한다. 자신의 마법 능력을 통해 왕국의 위기를 극복하려는 엘사, 언니를 잊지않기 위해 고군분투 하는 안나, 그런 안나에게 속마음을 고백하려는 크리스토프 등 얽히고 설킨 관계가 이야기 흐름의 중심적인 역할을 하게된다. 그만큼 캐릭터들의 감정과 행동이 이야기의 행방을 좌지우지하는 '열쇠'가 되는데, 문제는 이 핵심 캐릭터들이 조금만 엇나가더라도 이야기는 엉망이 될 수 있다는 점이다. 안타깝게도 이번 후속편은 일부 캐릭터들의 부조화스러운 문제를 노출해 이야기 흐름에 영향을 주는 단점을 드러냈다. 다소 지나치게 느껴지는 가족애적인 행동, 이야기에 큰 영향을 주지 않는 캐릭터의 핵심적 행동을 끝까지 이어가려 한 탓에 전형적인 이야기를 이어가게 되는 문제점, 그로인해 감초같은 일부 캐릭터들의 존재감이 애매해져 전편과는 다른 형태의 이야기가 되어버렸다. 시리즈의 장점이라 할 수 있는 뮤지컬 음악은 여전히 좋고 매력적이지만, 아쉽게도 전작의 'Let it go'와 같은 떼창을 불러올 정도는 아니다. 전작이 유년시절, 인물들의 상황에 따른 적절한 음악을 만들어냈던것과 달리, <겨울왕국 2>의 주제곡들은 인물의 혼란스러운 감정, 의지, 내면과 같은 지극히 개인적 상황을 중심으로 노래한다. 멋지게 감상할 수 있는 노래들이지만, 전작의 경쾌하고, 귀엽고, 귀에 남을 후렴구가 담긴 곡이 없어 이번에는 듣는데 집중해야 한다. 전작의 노래들이 히트한것을 의식한 탓에 너무 많은 음악을 선보이려다 상황에 어울리지 않은 음악들을 난립한 점도 아쉽게 다가온다. 그런 상황속에서도 우리의 핵심적인 캐릭터들은 여전히 매력이 넘친다. 마법을 자유자재로 사용하며 잠재된 위험들을 헤쳐나가며 핵심 사건을 향해 나아가는 엘사는 이제 이 시리즈의 히어로 같은 존재이며, 안나 역시 전작보다 성장한 모습을 보여줘 중요한 순간에 큰 활약을 선보인다. <겨울왕국>은 이번 영화에서도 두 자매의 존재감과 관계가 큰 역할을 하고 있음을 보여주며, 두 사람의 활약과 관계가 다른 작품에서 보기 드문 정서와 이야기를 만들어내고 있음을 보여준다. 덕분에 단점으로 다가왔던 이야기의 흐름도 이러한 특성 덕분에 후반부의 특별한 상황과 결과를 만들어내는 복선이 되었고, 이 시리즈의 지속적인 장기화를 기대해도 될 법한 흐름을 만들어 냈다. 스케일이 커지고 새로운 마법, 상황을 그린만큼 이를 구현한 시각효과와 그림은 아름답고 우아하다. 중간중간 아쉬움을 느꼈더라도 이번 시리즈의 볼거리와 스케일을 감상한다면 그 아쉬움을 충분히 걷어낼 수 있다. 전작의 아성을 뛰어넘거나 완벽한 작품은 아니지만, <겨울왕국>만의 매력과 재미만큼은 여전한 작품이었으며, 마지막에 등장한 쿠키 영상을 통해 여전히 이 시리즈가 매력 있는 작품임을 각인시켜준다.",
            "안뇽하세욧@! 지니어리의 맛집일기! 오늘은 강남역 토끼정 다녀온 후기를 말씀드릴려고 합니닷! 서현에서 직장다닐 때 직장 근처에 있고 수원역에도 있어서 자주 갔던 토끼정 강남이 본점이라는 이야기는 많이 들었었는데 이번에 드디어 토끼정 본점인 강남점 한번 가봤습니다!ㅎㅎ 저희가 강남 토끼정에 간 시간은 토요일 오후 5시가 조금 넘었던 시간인데 그 시간에는 웨이팅이 아예 없었어요! 하지만 저희가 아마 마지막테이블로 입장할 수 있었던 것 같아요!ㅎㅎ 강남 토끼정 건물은 2층과 지하로 이루어져있는데 2층 자리는 꽉찼다고 하셔서 저희는 지하로 내려갔답니다ㅎㅎ 저희가 계산하고 나갈 때는 본격적으로 웨이팅이 시작되고 있었어요! 입구에 줄서있는 많은 사람들을 보면서 일찍가길 잘했다 생각했답니다ㅎㅎ 토끼정 인테리어에요! 저 테이블 사람들이 먹고 나간 후 다른 테이블 손님이 들어오기 전 후다다다닥 사진을 촬영했답니닷ㅎㅎㅎ 모던하면서 깔끔한 이미지가 기분이 좋지 않나요?! 토끼정 인테리어는 어느 지점을 가도 맘에 들어요~ㅎㅎ 제가 원목을 정말 좋아하는데 토끼정은 전반적으로인테리어가 원목으로 되어있거든요!!ㅎㅎ 주문은 메뉴판을 보고 종이에 써서 벨을 누르면 종업원이 와서 작성한 종이를 가져가는 방식입니다! 저희는 음식을 주문하고 접시와 수저 젓가락, 물을 셋팅했어요 먹을 준비 완료 - ☆ 같이 찰칵 한 내 친구의 손ㅎㅎ 카와잉 저희가 시킨 메뉴는 크림카레우동, 돈까스오무카레밥, 레몬동동사이다 세가지였습니다!ㅎㅎ 남자친구한테 이렇게 시켰다고 사진보내줬더니 왜 둘이서 세개 안시키고 두개시켰냐고 물어봐서 당황스럽더군요;; 하지만 둘이서 세개 시킬껄 그랬나 살짝 후회되었어여....ㅎㅎㅎ 레몬 동동 사이다는 3,500원 이었는데 저랑 같이 간 친구 둘 다 사이다를 좋아해서 두개 시킬지 하나 시킬지 고민하다가 하나 먼저 시켰는데 양이 굉장히 많이 나오더라구요! 토끼정에서 음료 시키실 분들 하나만 시켜서 나눠드시면 될 것 같네요^^* 이것은 토끼정의 대표메뉴! 크림카레우동 이랍니다! 크림카레우동 비쥬얼 정말 최고라고 생각되지 않나요???!!!! 가격은 11,500원이었습니다! 맛은 솔직히말하면 저는 몇번 먹어봤던 맛이라서 익숙한 맛이었지만 친구는 정말 맛있다고 네그릇 먹고싶다고 하더라구여;; 하지만 맛있는건 정말 인정합니다!!ㅎㅎ 뭔가 일본식 카레인데 깔끔하고 담백한 맛! 꾸덕꾸덕한 크림을 생각하시면 안되요! 약간 물같은 카레국물의 크림카레우동이랍니다! 근데 가격에 비하면 양이 그렇게 많게 느껴지지는 않아요ㅠㅠ 친구랑 하나 더 시킬지 말지 정말 고민했었습니다ㅠㅠㅠ 그 다음으로 나온 메뉴는 돈까스오무카레밥! 돈까스를 튀기느라 시간이 오래걸려서 그런지 나오는데 시간은 좀 걸렸어요ㅠ 근데 돈까스가 너무너무 맛있었던 돈까스오무카레밥 가격은 11,000원으로 크림카레우동보다 500원 저렴한데 양은 진짜 거의 2배라고 할 수 있을 정도로 많았어요 아쉬웠던 점은 카레가 살짝 부족하다는거....? 카레가 부족해서 저희는 나중에 크림카레우동 국물에 찍어먹었어요ㅠㅠ 그 점이 굉장히 아쉬웠답니다ㅜㅜ 솔직히 가격이 저렴한 편은 아닌 토끼정 하지만 음식의 퀄리티나 맛이 굉장히 좋아요! 여자들이 좋아할 것 같은 분위기와 맛이라고 할까요??ㅎㅎㅎㅎ 저는 토끼정을 좋아하기 때문에 다음에 남자친구 데리고 또 갈 예정입니다! 강남역 토끼정의 위치는 서울 강남구 봉은사로6길 32 지번역삼동 812-5 토끼정 강남점 소소한 요리와 맛있는 술을 판매합니다. 이제는 배달도 가능합니다. 다만 배달은 매장 인근 지역에서만 가능한 점 양해 부탁드립니다. map.naver.com 매일매일 점심 12:00에 오픈해서 밤 10:00까지 한다고 하네요! 마지막 주문은 21:00까지라고 하니 저녁에 가실 분들은 참고하시길 바래요! 참고로 강남 토끼정 근처 사시는 분들은 배달도 가능하다고 하네요~ 그리고 흡연하시는 분들은 흡연실이 따로 없어서 나가서 피셔야해요! 강남역 근처 매장에 주차할 공간이 당연이 없다는 건 알고 계시겠죠??? 강남역에 자동차 가지고 가는게 제일 멍청한것 같아요ㅠㅠㅠ 매번 주차때문에 너무 고생이거든요ㅠㅠㅠ흑흑 이상 지니어리의 맛집일기 '토끼정'리뷰였습니다!",
            "안녕하세요 지니어리의 맛집일기! 오늘은 수원역 저만의 단골술집 마녀들의 살롱을 소개시켜 드리려고 합니다! 진짜 저만의 맛집이에여.... 제가 수원역 갈 때 마다 가는 요새 최애 술집이랍니다!ㅎㅎㅎ 짜잔~ 이게 바로 마녀들의 살롱 내부인데 굉장히 고급스럽지 않나요????!!!!! 진짜 고급스러운 인테리어에 여자들 취향 탕탕 저격할 마녀들의 살롱 마녀들의 살롱은 수제맥주와 와인 그리고 다양한 안주들을 판매하는 곳이랍니다! 굉장히 저렴한데 맛도 있어서 제가 굉장히 애정해요....ㅎㅎㅎ 그리고 특히 사장님이 굉장히!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 친절하세요!!!!!!!!!ㅎㅎ 친절왕 사장님!! 진짜 가시면 다들 느끼실 수 있을거에요!! 왕왕왕 친절하신 사장님을!!! 짜잔 주문을 하면 이렇게 맥주가 먼저 나와요! 저희는 피치에일이라는 맥주와 아메리칸 페일에일 이라는 맥주를 시켰어요~! 피치에일은 7,500원이고 아메리칸 페일에일은 5,500원 이랍니다! 맥주 색깔이 정말 예쁘지 않나요???!!!! 색만 예쁜게 아니라 맛도 좋아요! 수제맥주집은 많이 다녀봤는데 여기 맥주 잘합니다!! 저는 개인적으로 피치에일보다는 아메리칸 페일에일이 더 맛있었어요! 하지만 술을 별로 안좋아하시는 분들은 피치에일을 더 좋아할 것 같아요ㅎㅎ 달달하면서 향도 좋거든요~ 참고로 여기 기본안주로 나오는 과자 정말정말 맛있습니다... 이거 진짜 맥주안주로 최고에요ㅠㅠ 아니 사장님 어디서 이런 과자 구하셨어여... 집에다가 한포대 사놓고 맥주마실 때마다 마시고 싶어여!!!! 저희가 시킨 두번째 술은 바로 비나 마이포 소비뇽 사보레 라는 와인이에요 마녀들의 살롱에서는 와인을 정말 저렴한 가격에 마실 수 있는데 한잔 씩도 팔고 한 병씩도 판답니다~ 비나 마이포 소비뇽 사보레는 13도로 도수가 낮은 와인은 아닌데 와인을 처음 마시는 사람들도 쉽게 마실 수 있는 맛이에요! 저는 와인을 잘 못마시는데도 맛있게 먹었답니닷ㅎㅎ 하지만.... 결국에는 취하더라구여...^^* 원래 소주 시키려고 했는데... 마녀들의 살롱에서는 소주를 팔지 않는다고 하네요ㅠㅠ 여러분들도 참고하세요! ((소주를 안판다고 와인시킨)) 짜잔 제 마녀들의 살롱 최애안주 루꼴라피자에요!! 정말 맛있어보이지 않나요???!!!! 처음 마녀들의 살롱에 왔을 때 사장님 추천받아서 시켰었는데 그 맛에 반해.... 그 후로 여기 올때마다 매번 꼭 시키는 피자에요 진짜.... 존맛탱 그자체ㅠㅠㅠ 마녀들의 살롱 메뉴가 많아서 뭐 드실지 고민하시는 분들 루꼴라피자 진짜 꼭 드세요 두번드세요 너무너무 맛있어요!!!!!! 같이 간 친구들도 다들 맛있다는거 인정했답니다~~~~~~~ 가격은 11,000원인데... 11,000원 값어치 이상하는 맛의 피자ㅠㅠ 소스가 진짜 맛있어여.... 다만 조금 아쉬운건.... 바로 루꼴라가 마지막에 조금 아주조금 부족하다는거......? 그거 말고는 진짜 제가 먹은 루꼴라피자중에서 최고에여 이건 이번에 처음 시켜본 마녀순살후라이드치킨!!! 우선 비쥬얼 합격이구요!! 양도 합격입니다!! 가격은 12,000원인데 진짜 저렴하고 양많은 안주로 최고! 감자튀김도 많이 나오고... 소스도 세개나 나와 진짜 최고!!! 튀김은 갓 나온게 맛있다는건 정말 트루에요... 갓튀긴 치킨은 정말 맛있답니다~~~~~~~ 솔직히 가성비 안주로는 최강인듯 하네여.... 둘이서 마녀들의 살롱 가서 안주 두개시켰더니 배가 터져서 죽어버리는 줄 알았어여.... 그정도로 양이 많습니다...ㅎㅎㅎ 저녁을 안먹고 갔는데도 배불러ㅠㅠㅠㅠㅠㅠ 분위기도 좋고 안주 맛도 좋은 마녀들의 살롱 여자들이 딱 좋아할만한 분위기에 친절한 사장님까지~!!! 제가 요새 수원역에서 가장 애정하는 술집입니다....ㅎㅎㅎㅎ 수원역 마녀들의 살롱 위치는 경기 수원시 팔달구 매산로 21-11 지번매산로1가 41-6 지하1층 마녀들의살롱 치명적인 매력을 지닌 그녀들의 공간 \"마녀들의살롱\" 다양한 수제맥주와 와인 아늑한 분위기에 대화하기 좋은 공간 \"마녀들의살롱\"에 당신을 초대합니다. map.naver.com 수원역 마녀들의 살롱에는 흡연실이 없어요 여러분!! 올라가셔서 흡연하셔야 합니닷! 주차시설은 당연히..... 없습니닷!!! 수원역 술집에서 주차할곳을 찾는다는건 사치...^^* 한신포차 바로 맞은편에 있어서 찾기는 정말 수월해요!!ㅎㅎ 여러분들도 꼭 한번 가보세요~~ 정말 만족하실거에요!!!! 저렴하고 가성비 좋은 수원역 술집 분위기 좋고 애인이랑 가기 좋은 술집 기념일에 가볍에 와인한잔 하기 좋은 술집 친구들이랑 인생사진 찍을 수 있는 술집 안주가 맛있는 술집 사장님이 친절해서 기분좋아지는 술집 수원역 최고의 술집으로 추천합니다~~~~~~~~~~~~",
            "신서희 (동양일보 신서희 기자) 세종시가 안정적인 하수처리시설 기반 확충과 보건위생 환경 및 하천수질 개선을 위해 2019년 총 333억 원을 투입해 공공하수처리시설 설치 등 13개 사업을 추진한다 시는 경제 활성화와 일자리 창출을 위해 2019년 하수도사업 예산을 100% 집행한다는 목표를 설정하고, 신규 발주사업의 기술검토 및 재원협의, 공사입찰등 행정절차 기간을 최대한 단축하기로 했다. 특히 계속사업은 동절기 공사중지를 예년보다 15일 앞당겨 2월중순 해제하고, 공사관계자가 참여하는 ’예산집행대책반‘을 분기별로 운영하는 등 사업추진에 전력을 다할 계획이다. 주요 사업내용은 읍·면지역 공공하수처리시설 확충을 통한 안정적인 하수처리시설기반 구축을 위해 ▲국곡리 공공하수처리시설 설치사업 등 3개사업에 41억 원 등 총 13개 사업을 추진한다. 이외에도 ▲장군면 하수관로 정비사업 등 7개 사업(231억) ▲성덕리 마을하수도 정비사업 등3개 사업(61억)을 추진, 농촌지역 하수도 보급 향상을 통한 보건·환경격차를 해소해 나갈 계획이다. 또, ‘하수도정비기본계획 및 물재이용 관리계획’을 변경 수립해 하수처리구역 확대를 통해 하수도 보급률을 향상하고, 기후변화로 인한 물부족문제 대응을 위한 물재이용 증대방안을 마련할 계획이다. 임재환 상하수도과장은 “읍면지역 상하수도 인프라 구축 확대를 통해 시민 모두가 깨끗하고 안정적인 물 복지 혜택을 누릴 수 있도록 최선을 다해 나가겠다”고 밝혔다. 세종 신서희 기자",
            "신서희 47개 초등학교 총 3868명 배정, 지난해보다 773여명 증가 (동양일보 신서희 기자) 세종시교육청이 세종시에서 오는 3월 중학교로 진학하게 되는 초등학생들에 대한 「2019학년도 중학교 신입생 배정 결과」를 세종시교육청 누리집(http://www.sje.go.kr)에 발표했다고 31일 밝혔다. 이번 배정 대상은 세종시 소재 초등학교 졸업 예정자로 49개 초등학교(세종시 인접 초등학교 2교 포함) 총 3868명 규모(2018학년도 3095명)로 전년대비 773명(24.97%) 증가한 수치이다. 2019학년도 중학교에 입학할 신입생들은 지난 26일 세종시교육청 2층 대강당에서 학부모와 해당학교 교직원들이 참석한 가운데 진행한 공개 전산 추첨을 통해 관내 24개 중학교(예정지역 중학구의 경우 신설학교인 반곡중 포함)에 각각 배정됐다. 2019학년도 중학교 신입생 배정원서 접수결과, 학교군 예정지역 희망자 3,344명중 1지망 희망학교에 배정된 학생은 3128명(93.5%), 2지망 희망학교에 배정된 학생은 166명(5%)이었으며, 1, 2지망 학교에 배정되지 못하고 근거리 순으로 배정된 학생은 50명(1.5%)이며 중학구 524명은 해당 중학교에 배정됐다. 배정결과는 세종시교육청 누리집에서 확인할 수 있으며 개인별 문자메시지로도 통보될 예정이다. 배정통지서를 배부 받은 학생은 1월 9일부터 11일까지 해당학교로 입학등록을 마쳐야 한다. 세종 신서희 기자",
            "안준철 기자 매경닷컴 MK스포츠(대전) 안준철 기자  SK와이번스가 최종전에서 소중한 승리를 챙겼다. 반경기 차 단독 1위 자리를 탈환했고, 하루 뒤 두산 베어스의 경기를 지켜봐야 한다.  SK는 30일 대전 한화생명이글스파크에서 열린 한화 이글스와의 2019 KBO리그 정규시즌 최종전에서 선발 김광현의 호투와 김강민의 투런홈런 등을 앞세워 6-2로 승리했다. 이날 승리로 SK는 한화와의 대전 2연전을 모두 승리하며 시즌 최종 전적을 88승 1무 55패로 마치게 됐다. 이날 경기가 없던 두산을 0.5경기 차로 앞선 1위가 됐고, 이제 10월1일 잠실에서 있을 두산과 NC다이노스 경기를 지켜봐야 한다.  1위 싸움은 시즌 최종일인 10월1일까지 가게 됐다. SK의 승리로, 두산이 정규시즌 우승을 확정지으려면 NC전을 이겨야 한다. 두산이 SK와의 상대전적에서 9승7패로 앞서기에 두산과 SK가 동률이 되면 두산이 1위가 된다.   SK가 정규시즌 우승의 희망을 끝까지 살렸다. 사진=MK스포츠 DB 어쨌든 이날 승리로 SK가 유리한 고지에 오른 건 사실이다. 무조건 이겨야 한다는 압박감이 있었지만, SK는 초반부터 이날 경기를 잘 풀었다. 2회초 만만치 않은 한화 선발 채드벨을 상대로 4점을 뽑아 주도권을 잡았다. 선두타자 이재원의 안타에 이어 후속타자 김강민의 좌월 투런포가 터지면서 2-0을 리드를 잡았다.  이어 흔들리는 채드벨을 상대로 정현이 볼넷을 골랐고, 김성현의 희생번트로 1사 2루를 만든 뒤 노수광의 2루타로 1사 2,3루를 만들었다. 여기서 배영섭의 2타점 적시타로 4-0으로 점수를 벌렸다.  이후 김광현의 호투로 지키기 모드에 들어갔다. 다만 김광현도 3회 1사 후 장진혁과 정은원에 연속안타를 맞은 뒤 오선진의 희생플라이로 1실점으로 추격을 허용하고 말았다. 물론 계속된 위기 흐름을 범타로 끊는데 성공했다.  하지만 김광현은 7회말 선두타자 최진행에 좌월 솔로홈런을 내주면서 2점 차로 쫓기게 됐다. 이후 1사 후 최재훈에게도 안타를 내준 김광현은 장진혁을 중견수 뜬공으로 잡았지만, 2사 2루 위기를 맞았다. 여기서 정은원이 삼진으로 물러나면서 SK는 한 숨 돌렸다.  SK는 잠그기 모드에 들어갔다. 8회 서진용이 한화 타선을 막았고, 9회초 SK타선은 2점을 더 추가하며 승부에 쐐기를 박았다. 9회는 하재훈이 책임졌다. jcan1231@maekyung.com",
            "도내 산불 6년간 475건 발생 원인제공자 검거 186건 그쳐 입산자 실화 혐의입증 어려워 소송 처분까지 장시간 소요   지난해 연말 삼척 미로면 산불부터 새해 첫날 산림 20ha를 잿더미로 만든 양양 산불 등 실화로 추정되는 산불이 잇따르고 있지만 산불 실화자 검거율은 절반에도 미치지 못하고 있다.2일 산림청에 따르면 지난 6년간(2013~2018년) 도내에서 발생한 산불은 모두 475건으로,이 중 186건의 산불원인제공자를 검거했다.산불원인제공자 검거율은 약 39％ 정도로 절반에 미치지 못하는 실정이다.  지난 2017년 5월 6일 산림 765㏊와 주택 4채를 태운 삼척 도계 산불의 경우 실화로 추정됐지만 원인제공자를 특정하지 못해 원인미상으로 남겨진 상태다.같은 날 산림 252㏊와 주택 42채를 태우고 이재민 80여명이 발생했던 강릉 성산면 산불도 입산자의 의한 실화로 추정되지만 검거되지 못한 상태다.지난해 2월 삼척 도계에서 발생한 산불 역시입산자 실화로 추정되지만 1년 가까이 용의자를 특정하지 못하고 있다.  산림청은 입산자 실화는 증거 입수와 혐의 입증이 어려워 검거율이 낮을 수 밖에 없다는 입장이다.산림청 관계자는 “건물 화재나 논·밭 소각으로 인한 화재,성묘객 화재의 경우 증거 입수와 용의자 특정이 쉽지만 입산자 실화는 산중턱에서 발생하는 경우가 많아 용의자를 특정하기 어려운 실정”이라며 “산불 발화 지점은 찾을 수 있지만 진화작업으로 현장이 훼손되는 경우가 많아 결정적인 물증 찾기가 쉽지 않다”고 말했다.  실화자를 검거하더라도 이에대한 고의성을 입증하기가 어렵고 각종 소송으로 인해 처분에도 시간이 오래 걸리는 것도 문제다.2018년 5월 6일~9일 무려 161㏊의 산림 면적이 훼손된 삼척 노곡 산불의 경우 용의자를 특정,검찰에 송치됐지만 무혐의 종결됐으며 같은해 3월 28일 산림 358㏊를 태운 고성 산불의 경우 민사 소송이 화재가 발생한지 1년 가까이 되고 있지만 송사가 진행 중이다.  산림청 관계자는 “손해배상 청구소송의 경우 명확한 가해자와 완벽한 물증이 있어야 하지만 산불 실화의 특성상 입증이 쉽지 않고 시일이 오래 걸린다”고 말했다. 윤왕근",
            "도내 산불 6년간 475건 발생 원인제공자 검거 186건 그쳐 입산자 실화 혐의입증 어려워 소송 처분까지 장시간 소요   지난해 연말 삼척 미로면 산불부터 새해 첫날 산림 20ha를 잿더미로 만든 양양 산불 등 실화로 추정되는 산불이 잇따르고 있지만 산불 실화자 검거율은 절반에도 미치지 못하고 있다.2일 산림청에 따르면 지난 6년간(2013~2018년) 도내에서 발생한 산불은 모두 475건으로,이 중 186건의 산불원인제공자를 검거했다.산불원인제공자 검거율은 약 39％ 정도로 절반에 미치지 못하는 실정이다.  지난 2017년 5월 6일 산림 765㏊와 주택 4채를 태운 삼척 도계 산불의 경우 실화로 추정됐지만 원인제공자를 특정하지 못해 원인미상으로 남겨진 상태다.같은 날 산림 252㏊와 주택 42채를 태우고 이재민 80여명이 발생했던 강릉 성산면 산불도 입산자의 의한 실화로 추정되지만 검거되지 못한 상태다.지난해 2월 삼척 도계에서 발생한 산불 역시입산자 실화로 추정되지만 1년 가까이 용의자를 특정하지 못하고 있다.  산림청은 입산자 실화는 증거 입수와 혐의 입증이 어려워 검거율이 낮을 수 밖에 없다는 입장이다.산림청 관계자는 “건물 화재나 논·밭 소각으로 인한 화재,성묘객 화재의 경우 증거 입수와 용의자 특정이 쉽지만 입산자 실화는 산중턱에서 발생하는 경우가 많아 용의자를 특정하기 어려운 실정”이라며 “산불 발화 지점은 찾을 수 있지만 진화작업으로 현장이 훼손되는 경우가 많아 결정적인 물증 찾기가 쉽지 않다”고 말했다.  실화자를 검거하더라도 이에대한 고의성을 입증하기가 어렵고 각종 소송으로 인해 처분에도 시간이 오래 걸리는 것도 문제다.2018년 5월 6일~9일 무려 161㏊의 산림 면적이 훼손된 삼척 노곡 산불의 경우 용의자를 특정,검찰에 송치됐지만 무혐의 종결됐으며 같은해 3월 28일 산림 358㏊를 태운 고성 산불의 경우 민사 소송이 화재가 발생한지 1년 가까이 되고 있지만 송사가 진행 중이다.  산림청 관계자는 “손해배상 청구소송의 경우 명확한 가해자와 완벽한 물증이 있어야 하지만 산불 실화의 특성상 입증이 쉽지 않고 시일이 오래 걸린다”고 말했다. 윤왕근"};
    
//    String newsData = "우귀화 기자 wookiza@idomin.com  노동계는 고 염호석 씨 사건 등 삼성 노조 와해 사건의 '몸통'을 수사할 것을 촉구했다. 민주노총 경남지역본부는 '구속 수사하라! 빠르게 재판하라! 몸통을 수사하라!'는 논평을 냈다.  검찰은 염호석 금속노조 삼성전자서비스지회 양산분회장 시신 탈취 사건과 관련해 경찰관 2명을 불구속 기소했다. 이에 대해 민주노총 경남본부는 \"우리는 1년 이상의 하한형을 둔 부정처사후수뢰죄가 있음에도 이언학 서울중앙지법 영장전담 부장판사가 (경찰관 2명에 대해) '피의자가 검찰 수사에 적극적으로 응하고 있으며 범행 당시 피의자의 지위와 역할 등을 고려했을 때 구속 필요성을 인정하기 어렵다'고 기각한 것은 어처구니없다\"고 비판했다.  이어 \"직권남용 등의 과정에서 윗선이 있었는지를 가려내고 경찰관의 불법 개입에 대한 모든 진실을 밝히기 위해서라도 검찰은 구속영장을 재청구하고 서울중앙지법은 구속할 것을 촉구한다\"고 밝혔다.  또, \"검찰이 전사적 역량이 동원된 삼성의 조직적인 범죄에 대해 지난 9월 이상훈(63) 삼성전자 이사회 의장 등 32명을 한꺼번에 재판에 넘겼다. 하지만 검찰이 반헌법적인 노조 와해 공작의 '윗선'을 이상훈 의장이라고 결론 낸 것에 대해서는 알아듣기 어렵다. 우리는 검찰이 다시 몸통을 수사하고, 법원은 빠르게 재판할 것을 촉구한다\"며 삼성 이재용 부회장과 미래전략실 임원을 수사해야 한다고 주장했다. ";
    String newsData = "거산공인중개사 이명혜 대표는 9년 전 당진에 터를 잡았다. 그의 고향은 천안이지만 가족과 서울에서 오랫동안 살다가 \"남은 인생을 고향에서 보내고 싶다. \"는 남편의 말에 당진으로 내려왔다. 15년 동안 공인중개사로 일하고 있는 이명혜 대표는 \"지인의 사무실을 우연히 방문했는데 상담하는 모습이 상당히 전문적이었다\"며 \"그때부터 어느 한 분야에 전문성을 갖고 일하고 싶다는 생각이 들었다\"고 말했다.";
    @BeforeAll
    static void initializeTest() {
        TestFileInitializer.initialize();
    }

    @AfterAll
    static void tearDownTest() {
        TestFileInitializer.tearDown();
    }


    @Test
    public void testInitSplitter() {

        Splitter basicSplitter =  SplitterManager.getInstance().getSplitter();
        BeginEnd[] beginEnds = basicSplitter.split(data[0]);

        assertEquals(28, beginEnds[0].getEnd());
        assertEquals(29, beginEnds[1].getBegin());

    }


    @Test
    public void testSplit() {

        List<String> data1000 = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            data1000.add(data[0]);
        }

        long start = System.currentTimeMillis();

        for (int USED_CASE = 0 ; USED_CASE < data1000.size() ; USED_CASE++) {

            Splitter splitterImpl = SplitterManager.getInstance().getSplitter();

            splitterImpl.split(data1000.get(USED_CASE));

        }


        long end = System.currentTimeMillis();

//        System.out.println( "Non thread 실행 시간 : " + ( end - start )/1000.0 );
    }



    @Test
    public void testSplitEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> SplitterManager.getInstance().getSplitter().split(""));


    }

    @Test
    public void testSplitNullData() {
        assertThrows(IllegalArgumentException.class, () -> SplitterManager.getInstance().getSplitter().split(null));

    }



    @Test
    public void testOtherSplitter() {

        Splitter splitter = SplitterManager.getInstance().getSplitter("test");

        int[] beginAnswer = {0, 33, 106};
        int[] endAnswer = {32, 105, 222};

        BeginEnd[] beginEnds = splitter.split(newsData);

        assertEquals(endAnswer.length, beginEnds.length);

        int index = 0;
        for(BeginEnd beginEnd : beginEnds) {

            assertEquals(beginAnswer[index], beginEnd.getBegin());
            assertEquals(endAnswer[index++], beginEnd.getEnd());
        }



    }

    @Test
    public void testEditConditionInMemory() {

        Splitter splitter = SplitterManager.getInstance().getSplitter("test");

        ((RuleSplitter)splitter).addSplitConditionInMemory(new SplitCondition.Builder("이지만", 'B').build());

        int[] beginAnswer = {0, 33, 46, 106};
        int[] endAnswer = {32, 45, 105, 222};

        BeginEnd[] splitResults = splitter.split(newsData);
        assertEquals(endAnswer.length, splitResults.length);


        int index = 0;
        for (BeginEnd splitResult : splitResults) {

            assertEquals(beginAnswer[index], splitResult.getBegin());
            assertEquals(endAnswer[index++], splitResult.getEnd());
        }

        int[] beginAnswer2 = {0, 33, 106};
        int[] endAnswer2 = {32, 105, 222};

        ((RuleSplitter)splitter).deleteSplitConditionInMemory(new SplitCondition.Builder("이지만", 'B').build());
        splitResults = splitter.split(newsData);

        assertEquals(endAnswer2.length, splitResults.length);

        index = 0;
        for (BeginEnd splitResult : splitResults) {
            assertEquals(beginAnswer2[index], splitResult.getBegin());
            assertEquals(endAnswer2[index++], splitResult.getEnd());
        }
    }

    @Test
    public void testEditPatternConditionInMemory() {
        Splitter splitter = SplitterManager.getInstance().getSplitter("test_regx");
        int[] beginAnswer1 = {0, 46, 113};
        int[] endAnswer1 = {45, 112, 222};

        ((RuleSplitter)splitter).addSplitConditionInMemory(new SplitCondition.Builder("천...만", 'B').isPattern().build());
        ((RuleSplitter)splitter).addSplitConditionInMemory(new SplitCondition.Builder("공인중개사로", 'F').build());
        ((RuleSplitter)splitter).addSplitConditionInMemory(new SplitCondition.Builder("앍돡", 'F').build());

        BeginEnd[] splitResults = splitter.split(newsData);

        assertEquals(endAnswer1.length, splitResults.length);

        int index = 0;
        for (BeginEnd splitResult : splitResults) {
            assertEquals(beginAnswer1[index], splitResult.getBegin());
            assertEquals(endAnswer1[index++], splitResult.getEnd());
        }


        int[] beginAnswer2 = {0};
        int[] endAnswer2 = {222};

        ((RuleSplitter)splitter).deleteSplitConditionInMemory(new SplitCondition.Builder("천...만", 'B').isPattern().build());
        ((RuleSplitter)splitter).deleteSplitConditionInMemory(new SplitCondition.Builder("공인중개사로", 'F').build());


        splitResults = splitter.split(newsData);

        assertEquals(endAnswer2.length, splitResults.length);

        index = 0;
        for (BeginEnd splitResult : splitResults) {
            assertEquals(beginAnswer2[index], splitResult.getBegin());
            assertEquals(endAnswer2[index++], splitResult.getEnd());
        }



    }

    @Test
    public void testEditValidationInMemory() {

        int[] beginAnswer = {0, 33, 46, 106};
        int[] endAnswer = {32, 45, 105, 222};

        // 해당 Splitter 를 "test"로 변경하면 testEditConditionInMemory 테스트와 동시 실행 중 "이지만" 조건이 삭제되어 NPE 발생생
       Splitter splitter = SplitterManager.getInstance().getSplitter();

        ((RuleSplitter)splitter).addSplitConditionInMemory(new SplitCondition.Builder("이지만", 'B').build());

        BeginEnd[] splitResults = splitter.split(newsData);
        assertEquals(endAnswer.length, splitResults.length);
        int index = 0;
        for(BeginEnd splitResult : splitResults) {
            assertEquals(beginAnswer[index], splitResult.getBegin());
            assertEquals(endAnswer[index++], splitResult.getEnd());
        }


        int[] beginAnswer2 = {0, 33, 106};
        int[] endAnswer2 = {32, 105, 222};

        ((RuleSplitter)splitter).addValidationInMemory(new Validation(" 가족", false, 'B'));
        ((RuleSplitter)splitter).addValidationInMemory(new Validation(" 가족", false, 'F'));

        splitResults = splitter.split(newsData);

        assertEquals(endAnswer2.length, splitResults.length);

        index = 0;
        for(BeginEnd splitResult : splitResults) {
            assertEquals(beginAnswer2[index], splitResult.getBegin());
            assertEquals(endAnswer2[index++], splitResult.getEnd());
        }

        ((RuleSplitter)splitter).deleteValidationInMemory(new Validation(" 가족", false, 'B'));
        ((RuleSplitter)splitter).deleteValidationInMemory(new Validation(" 가족", false, 'F'));

        splitResults = splitter.split(newsData);
        assertEquals(endAnswer.length, splitResults.length);

        index = 0;
        for(BeginEnd splitResult : splitResults) {
            assertEquals(beginAnswer[index], splitResult.getBegin());
            assertEquals(endAnswer[index++], splitResult.getEnd());
        }
    }



    @Test
    public void testSplitWithRegx() {
        Splitter splitter = SplitterManager.getInstance().getSplitter("test_regx");

        String data = "지금부터 우리학교 규칙을 설명하겠습니다. 앞에 게시판을 보면 1. 교실에서는 조용히 하기 2. 복도에서 뛰어다니지 않기 3. 지각하면 벌금내기 입니다.";
        int[] beginAnswer = {0, 34, 50, 67};
        int[] endAnswer = {33, 49, 66, 84};

        BeginEnd[] splitResults = splitter.split(data);

        assertEquals(beginAnswer.length, splitResults.length);
        int answerIndex = 0;
        for (BeginEnd splitResult : splitResults) {
            assertEquals(beginAnswer[answerIndex], splitResult.getBegin());
            assertEquals(endAnswer[answerIndex++], splitResult.getEnd());
        }
    }

    @Test
    public void testNearSplitPoint() {
        String data = "다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.";
        int[] answer = {10, 16, 22, 28, 34, 40, 46, 52, 58, 64};
        Splitter splitter = SplitterManager.getInstance().getSplitter("test");
        BeginEnd[] splitResults = splitter.split(data);

        assertEquals(answer.length, splitResults.length);

        int i = 0;
        for (BeginEnd splitResult : splitResults) {
            assertEquals(answer[i++], splitResult.getEnd());
        }

        splitter = SplitterManager.getInstance().getSplitter("test_rule_loop");
        splitResults = splitter.split(data);

        assertEquals(answer.length, splitResults.length);

        i = 0;
        for (BeginEnd splitResult : splitResults) {
            assertEquals(answer[i++], splitResult.getEnd());
        }

    }

    @Test
    public void testEmptyStrings() {
        String emptyStr = "거산공인중개사 이명혜 대표는 9년 전 당진에 터를 잡았다.      그의 고향은 천안이지만 가족과 서울에서 오랫동안 살다가 \"남은 인생을 고향에서 보내고 싶다. \"는 남편의 말에 당진으로 내려왔다.         15년 동안 공인중개사로 일하고 있는 이명혜 대표는 \"지인의 사무실을 우연히 방문했는데 상담하는 모습이 상당히 전문적이었다\"며 \"그때부터 어느 한 분야에 전문성을 갖고 일하고 싶다는 생각이 들었다\"고 말했다.          ";

        Splitter splitter = SplitterManager.getInstance().getSplitter();
        BeginEnd[] splitResult = splitter.split(emptyStr);

        int[] beginAnswer = {0, 38, 119};
        int[] endAnswer = {32, 110, 235};

        assertEquals(beginAnswer.length, splitResult.length);

        int index = 0;
        for (BeginEnd beginEnd : splitResult) {
            assertEquals(beginAnswer[index], beginEnd.getBegin());
            assertEquals(endAnswer[index++], beginEnd.getEnd());
        }
    }

    @Test
    public void testNotIncludeSplitCondition() {
        String data = "해맞이 명소로 손꼽히는 강릉 정동진, 울산 간절곶, 포항 호미곶, 부산 해운대 등에는 해맞이객이 해안선을 따라 길게 늘어섰다.\\r\\n\\r\\n황금돼지해의 첫 태양이 수평선 위로 모습을 드러내자 해맞이객들은 탄성을 터뜨리며 가족·연인과 함께 저마다 간직한 새해 소망을 빌었다.";
        Splitter splitter = SplitterManager.getInstance().getSplitter("paragraph");

        BeginEnd[] splitResult = splitter.split(data);



        assertEquals(0, splitResult[0].getBegin());
        assertEquals(70, splitResult[0].getEnd());

        assertEquals(78, splitResult[1].getBegin());
        assertEquals(152, splitResult[1].getEnd());;



    }

    @Test
    public void testNoConditionSplitter() {
        String data = "test";
        assertThrows(RuntimeException.class, () -> {
            Splitter splitter = SplitterManager.getInstance().getSplitter("test_no_condition");
            ((RuleSplitter) splitter).deleteSplitConditionInMemory(new SplitCondition.Builder("removed", 'F').build());
            splitter.split(data);
        });

    }

    @Test
    public void testFindByManyRuleLoop() {
        Splitter splitter = SplitterManager.getInstance().getSplitter("test_rule_loop");
        int[] beginAnswer = {0, 33, 106};
        int[] endAnswer = {32, 105, 222};


        BeginEnd[] splitResult = splitter.split(newsData);

        assertEquals(beginAnswer.length, splitResult.length);

        int index = 0;
        for (BeginEnd beginEnd : splitResult) {
            assertEquals(beginAnswer[index], beginEnd.getBegin());
            assertEquals(endAnswer[index++], beginEnd.getEnd());
        }

    }

}

