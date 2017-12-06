package zhou.com.snail.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhou on 2017/10/21.
 */

public class APIServer {

    private LoveServer server;

    private static APIServer apiServer;

    public static APIServer getInstence(){
        if(apiServer == null){
            synchronized (APIServer.class){
                if(apiServer == null){
                    apiServer = new APIServer();
                }
            }
        }
        return apiServer;
    }

    public LoveServer getServer(){
        if(server == null){

            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(new BaseInterceptor());
            client.readTimeout(1, TimeUnit.MINUTES).connectTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(1,TimeUnit.MINUTES);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LoveServer.BASE_URL)
                    .client(client.build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            server = retrofit.create(LoveServer.class);
        }

        return server;
    }
}

