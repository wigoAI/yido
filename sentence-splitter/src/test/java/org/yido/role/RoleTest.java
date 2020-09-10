package org.yido.role;

import org.bitbucket.eunjeon.seunjeon.Analyzer;
import org.bitbucket.eunjeon.seunjeon.Eojeol;
import org.bitbucket.eunjeon.seunjeon.LNode;
import org.junit.Test;
import org.yido.dataInput.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoleTest {

    /**
     *   한명의 리뷰 데이터를 조사한다.
     *   이 때 모든 문장을 구분한 데이터를 생성하기는 어려우므로
     *   모든 리뷰의 마지막은 문장의 끝인것을 이용하여
     *   마지막 어절의 데이터를 수집, 분석하여 룰을 생성한다.
     *
     *
     *   온점이 왔을 때 이전 어절 확인을 처리할 것
     *  */
    @Test
    public void createRoleTest() {
        FileReader filereader = new FileReader("/data/revData.txt");
//      role을 HashMap에 저장
        HashMap<String, Integer> roles = new HashMap<String, Integer>();




        for(String str : filereader.getSplitFile("|")) {
            System.out.println(str + " : " + str.length());

            // 어절 분석기
            for (Eojeol eojeol: Analyzer.parseEojeolJava(str)) {
                int targetOffset = str.length();

                List<String> list = new ArrayList<String>();


                // 해당 어절의 endOffset이 문장의 길이와 같다면 문장의 끝에 있는 어절이다.
                // 이 어절의 형태소를 분석한다.
                if(eojeol.endOffset() == str.length()) {
                    System.out.println(eojeol.surface());
                    List<LNode> hts = new ArrayList<LNode>();
                    for (LNode node : Analyzer.parseJava(eojeol.surface())) {
//                        System.out.print("[" + node + "]");
                        hts.add(node);

                    }

                    String key = hts.get(hts.size()-1).morpheme().getSurface();

                    if(roles.containsKey(key)){
                        roles.replace(key, roles.get(key) + 1);
                    } else {
                        roles.put(key, 1);
                    }
                    System.out.println(hts.get(hts.size()-1).morpheme().getSurface());
//                    System.out.println();
                }
//                System.out.print(eojeol.surface() + " : ");
//                System.out.print(eojeol.beginOffset() + " ~ ");
//                System.out.println(eojeol.endOffset());

            }
        }

        System.out.println("role size : " + roles.size());
        System.out.println("role 다 : " + roles.get("다") );


    }
}
