package com.example.coinmarket.Repository;


import com.example.coinmarket.Retrofit.RequestApi;
import com.example.coinmarket.PojoModels.AllMarketModel;

import io.reactivex.rxjava3.core.Observable;

public class AppRepository {

    RequestApi requestApi;


    public AppRepository(RequestApi requestApi) {
        this.requestApi = requestApi;

    }
    public Observable<AllMarketModel> marketListFutureCall(){
        return requestApi.makeMarketLatestListCall();
    }
    //Call Api
//    public Future<Observable<AllMarketModel>> marketListFutureCall(){
//
//        //Executor
//          final ExecutorService executorService =Executors.newSingleThreadExecutor();
//
//          //Callable
//          final Callable<Observable<AllMarketModel>> myCall=new Callable<Observable<AllMarketModel>>() {
//              @Override
//              public Observable<AllMarketModel> call() throws Exception {
//                  return requestApi.makeMarketLatestListCall();
//              }
//          };
//
//          //Future
//        final Future<Observable<AllMarketModel>> future=new Future<Observable<AllMarketModel>>() {
//            @Override
//            public boolean cancel(boolean mayInterruptIfRunning) {
//                if(mayInterruptIfRunning){
//                    executorService.isShutdown();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean isCancelled() {
//                return executorService.isShutdown();
//            }
//
//            @Override
//            public boolean isDone() {
//                return executorService.isTerminated();
//            }
//
//            //submit Executor and then Call Callable
//            @Override
//            public Observable<AllMarketModel> get() throws ExecutionException, InterruptedException {
//                return executorService.submit(myCall).get();
//            }
//
//            @Override
//            public Observable<AllMarketModel> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
//                return executorService.submit(myCall).get(timeout,unit);
//            }
//        };
//        return future;
//    }

}
