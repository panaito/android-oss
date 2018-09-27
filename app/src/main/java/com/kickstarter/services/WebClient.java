package com.kickstarter.services;

import android.support.annotation.NonNull;

import com.kickstarter.models.Empty;
import com.kickstarter.services.apiresponses.InternalBuildEnvelope;

import retrofit2.Response;
import rx.Observable;
import rx.schedulers.Schedulers;

import static com.kickstarter.libs.rx.transformers.Transformers.ignoreValues;

public final class WebClient implements WebClientType {
  private final WebService service;

  public WebClient(final @NonNull WebService service) {
    this.service = service;
  }

  public Observable<InternalBuildEnvelope> pingBeta() {
    return this.service.pingBeta()
      .filter(Response::isSuccessful)
      .map(Response::body)
      .subscribeOn(Schedulers.io());
  }
  @Override
  public Observable<Void> resendEmailVerification() {
    return this.service.resendVerification()
      .map(Response::body)
      .compose(ignoreValues())
      .subscribeOn(Schedulers.io());
  }
}
