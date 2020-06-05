import com.winson.TestServerApplication;
import com.winson.task.AsyncTask;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestServerApplication.class)
public class Test {

    @Resource
    public AsyncTask asyncTask;

    @org.junit.Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            asyncTask.getAuthorDetailAsync("winson-" + i);
        }
    }

    @org.junit.Test
    public void test2() throws Exception {
        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
    }
}
