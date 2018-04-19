package ruan.com.retrofit2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import ruan.com.Net.HttpManager.HttpResponse;
import ruan.com.Net.HttpManager.Interface.HttpCallback;
import ruan.com.Net.MqttManager.Interface.MqttCallback;
import ruan.com.Net.MqttManager.MqttManager;
import ruan.com.Net.MqttManager.MqttRequest;
import ruan.com.Net.UdpManager.Interface.UdpCallback;
import ruan.com.retrofit2.BaseControl.BaseActivity;

/**
 * Created by 19820 on 2018/1/4.
 */

public class MainActivity extends BaseActivity implements IView , HttpCallback.Response , UdpCallback.Response , MqttCallback.Response{


    public Context context;

    //当一个成员变量被@Inject注解修饰，并且它的类型构造函数也被@Inject注解修饰,dagger2就会自动实例化该成员类型，并注入到该成员变量
    @Inject
    TestPresent mPresent;


    /**
     * activity  start
     */
    @Override
    protected void init() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

//        final MqttRequest mqttRequest = new MqttRequest(context , this);
//        mqttRequest.init("test");

//        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mqttRequest.send("test" , "测试 android send data");
//            }
//        });


        EventBus.getDefault().register(this);

        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , TestActivity.class);
                startActivity(intent);
            }
        });


//        String msg = "你好 udp server";

//        new UdpRquest(context , this).request(msg.getBytes(Charset.forName("utf-8")) , "192.168.1.101" , 5066 , 1);

//        MqttManager mqttManager = new MqttManager(this);
//        mqttManager.



//        DaggerTestComponent.builder().githubApiModule(new GithubApiModule(this)).testModule(new TestModule(this)).build().inject(this);//@Component负责连接起@Inject和@Module注解
//        mPresent.updateUI();

//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .baseUrl("http://39.108.192.191:8080/fanghuaLaw/")
//                .build();

//        final RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setPage(1);
//        registerRequest.setType(0);

//        UserService apiService = retrofit.create(UserService.class);
//        Observable<?> observable = apiService.gettopicpage(1 , 0);
//        Observable<BaseObject> objectObservable = observable;

//        new HttpRequest(context , this).request(observable , 1);

//        new RequestApi(this).RequestTopic(this , 1);

//        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread())
//                .subscribe(
//                        new Observer<BaseObject>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                System.out.println(e.toString());
//                            }
//
//                            @Override
//                            public void onNext(BaseObject baseObject) {
//                                EncodeData encodeData = (EncodeData) baseObject;
//                                System.out.println(encodeData.toString());
//                            }
//                        }
//
//                );


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onMessage(ListBean bean){
        Toast.makeText(this , bean.getAddress() , Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onMessage(String bean){
        Toast.makeText(this , "string:" + bean , Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateUI(String text) {
        ((TextView) findViewById(R.id.textview)).setText(text);
    }

    /**
     * 请求成功
     *
     * @param requestCode 请求标识
     * @param response  请求返回的对象
     */
    @Override
    public void onSuccess(int requestCode, HttpResponse response) {
        EncodeData encodeData = (EncodeData) response;
        System.out.println("success:" + encodeData.toString());
    }

    /**
     * 请求失败
     *
     * @param requestCode 请求标识
     * @param throwable   请求失败的对象
     */
    @Override
    public void onError(int requestCode, Throwable throwable) {

    }

    /**
     * 请求成功
     *
     * @param requestCode 请求标识
     * @param msg         请求返回的数据
     */
    @Override
    public void onUdpSuccess(int requestCode, String msg) {
        Toast.makeText(context , msg , Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求失败
     *
     * @param requestCode 请求标识
     * @param throwable   请求失败的对象
     */
    @Override
    public void onUdpError(int requestCode, Throwable throwable) {
        Toast.makeText(context , throwable.toString() , Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求成功
     *
     * @param requestCode 请求标识
     * @param message     请求返回的数据
     */
    @Override
    public void onSuccess(String requestCode, String message) {
        Toast.makeText(context , requestCode + "--" + message , Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求失败
     *
     * @param requestCode 请求标识
     * @param throwable   请求失败的对象
     */
    @Override
    public void onError(String requestCode, Throwable throwable) {
        Toast.makeText(context , requestCode + "--" + throwable.toString() , Toast.LENGTH_SHORT).show();
    }
}

/**
 * 定义一个View层的统一接口
 * View接口保持不变
 */
interface IView {

    void updateUI(String text);

}


/**
 * Present类，调用Model层的业务方法，更新View层的界面展示
 */
class TestPresent {
    IView mView;
    @Inject
    TestModel mModel;//Dagger2遇到@Inject标记的成员属性，就会去查看该成员类的构造函数，如果构造函数也被@Inject标记,则会自动初始化，完成依赖注入。

    //TestPresent的构造函数也被@Inject注解修饰
    @Inject
    public TestPresent(IView view) {
        this.mView = view;
    }

    public void updateUI() {
        mView.updateUI(mModel.getText());
    }
}

/**
 * Model类，实现具体的业务逻辑
 */
class TestModel {
    //构造函数用@Inject修饰
    @Inject
    public TestModel() {
    }

    public String getText() {
        return "Dagger2应用实践...";
    }
}

/**
 * Module类提供那些没有构造函数的类的依赖，如第三方类库，系统类，接口类
 */
@Module
class TestModule {
    private IView mView;

    public TestModule(IView iView) {
        this.mView = iView;
    }

    //@Provides注解的方法，提供IView类的依赖。
    @Provides
    public IView provideIView() {
        return this.mView;
    }
}


/**
 * Module类提供那些没有构造函数的类的依赖，如第三方类库，系统类，接口类
 */
@Module
class GithubApiModule {

    private IView mView;

    public GithubApiModule(IView iView) {
        this.mView = iView;
    }
    //@Provides注解的方法，提供IView类的依赖。
//    @Provides
//    public IView provideIView(){
//        return this.mView;
//    }
}


/**
 * Component必须是一个接口类或者抽象
 */
@Component(modules = {TestModule.class, GithubApiModule.class})
interface TestComponent {
    void inject(MainActivity mainActivity);
}
