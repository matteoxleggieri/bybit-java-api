package com.bybit.api.client.domain.asset.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AssetInternalTransferRequest {
    private String transferId;
    private String coin;
    private String amount;
    private String fromAccountType;
    private String toAccountType;
}

