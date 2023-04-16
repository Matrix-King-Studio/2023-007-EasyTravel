package tour;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class TourApplicationTests {

    @Test
    void mp(){
        String s = new Md5Hash("123456").toHex();
        HashMap<Integer, Integer> map = new HashMap<>();

        for (Map.Entry<Integer,Integer> entry:map.entrySet()){
            entry.getValue();
        }


        System.out.println(s);
    }
}
