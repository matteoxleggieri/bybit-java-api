package com.bybit.api.client;

import com.bybit.api.client.domain.account.AccountDataRequest;

public interface BybitApiAsyncAccountRestClient {
    // Account endpoints
    void getWalletBalance(AccountDataRequest walletBalanceRequest, BybitApiCallback<Object> callback);
    void upgradeAccountToUTA(BybitApiCallback<Object> callback);
    void getAccountBorrowHistory(AccountDataRequest borrowHistoryRequest, BybitApiCallback<Object> callback);
    void setAccountCollateralCoin(AccountDataRequest setCollateralCoinRequest, BybitApiCallback<Object> callback);
    void getAccountCollateralInfo(AccountDataRequest request, BybitApiCallback<Object> callback);
    void getAccountCoinGeeks(AccountDataRequest request, BybitApiCallback<Object> callback);
    void getAccountFreeRate(AccountDataRequest getFeeRateRequest, BybitApiCallback<Object> callback);
    void getAccountInfo(BybitApiCallback<Object> callback);
    void getTransactionLog(AccountDataRequest getTransactionLogRequest, BybitApiCallback<Object> callback);
    void setAccountMarginMode(AccountDataRequest request, BybitApiCallback<Object> callback);
    void modifyAccountMMP(AccountDataRequest setMMPRequest, BybitApiCallback<Object> callback);
    void resetAccountMMP(AccountDataRequest request, BybitApiCallback<Object> callback);
    void getAccountMMPState(AccountDataRequest request, BybitApiCallback<Object> callback);
}