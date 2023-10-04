package com.bybit.api.client;

import com.bybit.api.client.constant.BybitApiConstants;
import com.bybit.api.client.domain.ProductType;
import com.bybit.api.client.domain.account.request.SetCollateralCoinRequest;
import com.bybit.api.client.domain.account.request.SetMMPRequest;
import com.bybit.api.client.domain.asset.request.AssetInternalTransferRequest;
import com.bybit.api.client.domain.asset.request.AssetUniversalTransferRequest;
import com.bybit.api.client.domain.asset.request.AssetWithdrawRequest;
import com.bybit.api.client.domain.c2c.ClientLendingFundsRequest;
import com.bybit.api.client.domain.position.request.*;
import com.bybit.api.client.domain.spot.leverageToken.SpotLeverageTokenRequest;
import com.bybit.api.client.domain.spot.marginTrade.SpotMarginTradeBorrowRequest;
import com.bybit.api.client.domain.spot.marginTrade.SpotMarginTradeRePayRequest;
import com.bybit.api.client.domain.trade.*;
import com.bybit.api.client.domain.user.request.ApiKeyRequest;
import com.bybit.api.client.domain.user.request.FreezeSubUIDRquest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Bybit's REST API URL mappings and endpoint security configuration.
 */
public interface BybitApiService {
    // Market data endpoints

    /**
     * Get Bybit Server Time
     * <a href="https://bybit-exchange.github.io/docs/v5/market/time">...</a>
     */
    @GET("/v5/market/time")
    Call<Object> getServerTime();

    /**
     * Query for historical mark price klines. Charts are returned in groups based on the requested interval.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     * <a href="https://bybit-exchange.github.io/docs/v5/market/kline">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. spot,linear,inverse
     * symbol	true	string	Symbol name
     * interval	true	string	Kline interval. 1,3,5,15,30,60,120,240,360,720,D,M,W
     * start	false	integer	The start timestamp (ms)
     * end	false	integer	The end timestamp (ms)
     * limit	false	integer	Limit for data size per page. [1, 1000]. Default: 200
     */
    @GET("/v5/market/kline")
    Call<Object> getMarketLinesData(@Query("category") String category,
                                    @Query("symbol") String symbol,
                                    @Query("interval") String interval,
                                    @Query("start") Long start,
                                    @Query("end") Long end,
                                    @Query("limit") Integer limit);

    /**
     * Query for historical mark price klines. Charts are returned in groups based on the requested interval.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     * <a href="https://bybit-exchange.github.io/docs/v5/market/mark-kline">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. spot,linear,inverse
     * symbol	true	string	Symbol name
     * interval	true	string	Kline interval. 1,3,5,15,30,60,120,240,360,720,D,M,W
     * start	false	integer	The start timestamp (ms)
     * end	false	integer	The end timestamp (ms)
     * limit	false	integer	Limit for data size per page. [1, 1000]. Default: 200
     */
    @GET("/v5/market/mark-price-kline")
    Call<Object> getMarketPriceLinesData(@Query("category") String category,
                                         @Query("symbol") String symbol,
                                         @Query("interval") String interval,
                                         @Query("start") Long start,
                                         @Query("end") Long end,
                                         @Query("limit") Integer limit);

    /**
     * Query for historical index price klines. Charts are returned in groups based on the requested interval.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     * <a href="https://bybit-exchange.github.io/docs/v5/market/index-kline">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. spot,linear,inverse
     * symbol	true	string	Symbol name
     * interval	true	string	Kline interval. 1,3,5,15,30,60,120,240,360,720,D,M,W
     * start	false	integer	The start timestamp (ms)
     * end	false	integer	The end timestamp (ms)
     * limit	false	integer	Limit for data size per page. [1, 1000]. Default: 200
     */
    @GET("/v5/market/index-price-kline")
    Call<Object> getIndexPriceLinesData(@Query("category") String category,
                                        @Query("symbol") String symbol,
                                        @Query("interval") String interval,
                                        @Query("start") Long start,
                                        @Query("end") Long end,
                                        @Query("limit") Integer limit);


    /**
     * Query for historical index price klines. Charts are returned in groups based on the requested interval.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/market/preimum-index-kline">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. spot,linear,inverse
     * symbol	true	string	Symbol name
     * interval	true	string	Kline interval. 1,3,5,15,30,60,120,240,360,720,D,M,W
     * start	false	integer	The start timestamp (ms)
     * end	false	integer	The end timestamp (ms)
     * limit	false	integer	Limit for data size per page. [1, 1000]. Default: 200
     */
    @GET("/v5/market/premium-index-price-kline")
    Call<Object> getPremiumIndexPriceLinesData(@Query("category") String category,
                                               @Query("symbol") String symbol,
                                               @Query("interval") String interval,
                                               @Query("start") Long start,
                                               @Query("end") Long end,
                                               @Query("limit") Integer limit);

    /**
     * Get Instruments Info
     * Query for the instrument specification of online trading pairs.
     * <p>
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     * <p>
     * CAUTION
     * Spot does not support pagination, so limit, cursor are invalid.
     * When query by baseCoin, regardless of category=linear or inverse, the result will have USDT perpetual, USDC contract and Inverse contract symbols.
     * <a href="https://bybit-exchange.github.io/docs/v5/market/instrument">...</a>
     *
     * @param category
     * @param symbol
     * @param status
     * @param baseCoin
     * @param limit
     * @param cursor
     * @return
     */
    @GET("/v5/market/instruments-info")
    Call<Object> getInstrumentsInfo(@Query("category") String category, @Query("symbol") String symbol, @Query("status") String status, @Query("baseCoin") String baseCoin,
                                    @Query("limit") Integer limit, @Query("cursor") String cursor);

    /**
     * Query for orderbook depth data.
     * <p>
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     * <p>
     * future: 200-level of orderbook data
     * spot: 50-level of orderbook data
     * option: 25-level of orderbook data
     * TIP
     * The response is in the snapshot format.
     * <a href="https://bybit-exchange.github.io/docs/v5/market/orderbook">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. spot,linear,inverse,option
     * symbol	false	string	Symbol name
     * status	false	string	Symbol status filter
     * spot/linear/inverse has Trading only
     * baseCoin	false	string	Base coin. linear,inverse,option only
     * For option, it returns BTC by default
     * limit	false	integer	Limit for data size per page. [1, 1000]. Default: 500
     * cursor	false	string	Cursor. Use the nextPageCursor token from the response to retrieve the next page of the result set
     */
    @GET("/v5/market/orderbook")
    Call<Object> getMarketOrderbook(@Query("category") String category, @Query("symbol") String symbol, @Query("limit") Integer limit);

    /**
     * Get Tickers
     * Query for the latest price snapshot, best bid/ask price, and trading volume in the last 24 hours.
     * <p>
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     * <p>
     * TIP
     * If category=option, symbol or baseCoin must be passed.
     * <a href="https://bybit-exchange.github.io/docs/v5/market/tickers">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. spot,linear,inverse,option
     * symbol	false	string	Symbol name
     * baseCoin	false	string	Base coin. For option only
     * expDate	false	string	Expiry date. e.g., 25DEC22. For option only
     */
    @GET("/v5/market/tickers")
    Call<Object> getMarketTickers(@Query("category") String category, @Query("symbol") String symbol, @Query("baseCoin") String baseCoin, @Query("expDate") String expDate);

    /**
     * Get Funding Rate History
     * Query for historical funding rates. Each symbol has a different funding interval. For example, if the interval is 8 hours and the current time is UTC 12, then it returns the last funding rate, which settled at UTC 8.
     * <p>
     * To query the funding rate interval, please refer to the instruments-info endpoint.
     * <p>
     * Covers: USDT and USDC perpetual / Inverse perpetual
     * <p>
     * TIP
     * Passing only startTime returns an error.
     * Passing only endTime returns 200 records up till endTime.
     * Passing neither returns 200 records up till the current time.
     * <a href="https://bybit-exchange.github.io/docs/v5/market/history-fund-rate">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear,inverse
     * symbol	true	string	Symbol name
     * startTime	false	integer	The start timestamp (ms)
     * endTime	false	integer	The end timestamp (ms)
     * limit	false	integer	Limit for data size per page. [1, 200]. Default: 200
     */
    @GET("/v5/market/funding/history")
    Call<Object> getFundingHistory(
            @Query("category") String category,
            @Query("symbol") String symbol,
            @Query("startTime") Long startTime,
            @Query("endTime") Long endTime,
            @Query("limit") Integer limit);

    /**
     * Get Public Recent Trading History
     * Query recent public trading data in Bybit.
     * <p>
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     * <p>
     * You can download archived historical trades here:
     * <p>
     * USDT Perpetual, Inverse Perpetual & Inverse Futures
     * <a href="https://bybit-exchange.github.io/docs/v5/market/recent-trade">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. spot,linear,inverse,option
     * symbol	false	string	Symbol name
     * required for spot/linear/inverse
     * optional for option
     * baseCoin	false	string	Base coin. For option only. If not passed, return BTC data by default
     * optionType	false	string	Option type. Call or Put. For option only
     * limit	false	integer	Limit for data size per page.
     * spot: [1,60], default: 60.
     * others: [1,1000], default: 500
     */
    @GET("/v5/market/recent-trade")
    Call<Object> getRecentTradeData(
            @Query("category") String category,
            @Query("symbol") String symbol,
            @Query("baseCoin") String baseCoin,
            @Query("optionType") String optionType,
            @Query("limit") Integer limit);

    /**
     * Get Open Interest
     * Get the open interest of each symbol.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     * <p>
     * INFO
     * Returns single side data
     * The upper limit time you can query is the launch time of the symbol.
     * <a href="https://bybit-exchange.github.io/docs/v5/market/open-interest">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear,inverse
     * symbol	true	string	Symbol name
     * intervalTime	true	string	Interval. 5min,15min,30min,1h,4h,1d
     * startTime	false	integer	The start timestamp (ms)
     * endTime	false	integer	The end timestamp (ms)
     * limit	false	integer	Limit for data size per page. [1, 200]. Default: 50
     * cursor	false	string	Cursor. Used to paginate
     */
    @GET("/v5/market/kline")
    Call<Object> getOpenInterest(
            @Query("category") String category,
            @Query("symbol") String symbol,
            @Query("intervalTime") String intervalTime,
            @Query("startTime") Long startTime,
            @Query("endTime") Long endTime,
            @Query("limit") Integer limit,
            @Query("cursor") String cursor);

    /**
     * Get Historical Volatility
     * Query option historical volatility
     * <p>
     * Covers: Option
     * <p>
     * INFO
     * The data is hourly.
     * If both startTime and endTime are not specified, it will return the most recent 1 hours worth of data.
     * startTime and endTime are a pair of params. Either both are passed or they are not passed at all.
     * This endpoint can query the last 2 years worth of data, but make sure [endTime - startTime] <= 30 days.
     * <a href="https://bybit-exchange.github.io/docs/v5/market/iv">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. option
     * baseCoin	false	string	Base coin. Default: return BTC data
     * period	false	integer	Period
     * startTime	false	integer	The start timestamp (ms)
     * endTime	false	integer	The end timestamp (ms)
     */
    @GET("/v5/market/historical-volatility")
    Call<Object> getHistoricalVolatility(
            @Query("category") String category,
            @Query("baseCoin") String baseCoin,
            @Query("period") Integer period,
            @Query("startTime") Long startTime,
            @Query("endTime") Long endTime);

    /**
     * Get Insurance
     * Query for Bybit insurance pool data (BTC/USDT/USDC etc). The data is updated every 24 hours.
     * <a href="https://bybit-exchange.github.io/docs/v5/market/insurance">...</a>
     *
     * @param coin
     * @return
     */
    @GET("/v5/market/insurance")
    Call<Object> getInsurance(@Query("coin") String coin);

    @GET("/v5/market/insurance")
    Call<Object> getInsurance();

    /**
     * Get Risk Limit
     * Query for the risk limit.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     * <a href="https://bybit-exchange.github.io/docs/v5/market/risk-limit">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear,inverse
     * symbol	false	string	Symbol name
     */
    @GET("/v5/market/risk-limit")
    Call<Object> getRiskLimit(@Query("category") String category,
                              @Query("symbol") String symbol);

    /**
     * Get Delivery Price
     * Get the delivery price.
     * <p>
     * Covers: USDC futures / Inverse futures / Option
     * <p>
     * HTTP Request
     * GET /v5/market/delivery-price
     * <a href="https://bybit-exchange.github.io/docs/v5/market/delivery-price">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear, inverse, option
     * symbol	false	string	Symbol name
     * baseCoin	false	string	Base coin. Default: BTC. valid for option only
     * limit	false	integer	Limit for data size per page. [1, 200]. Default: 50
     * cursor	false	string	Cursor. Use the nextPageCursor token from the response to retrieve the next page of the
     */
    @GET("/v5/market/delivery-price")
    Call<Object> getDeliveryPrice(@Query("category") String category,
                                  @Query("symbol") String symbol,
                                  @Query("baseCoin") String baseCoin,
                                  @Query("limit") Integer limit,
                                  @Query("cursor") String cursor);

    /**
     * Get Long Short Ratio
     * <a href="https://bybit-exchange.github.io/docs/v5/market/long-short-ratio">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear(USDT Perpetual only),inverse
     * symbol	true	string	Symbol name
     * period	true	string	Data recording period. 5min, 15min, 30min, 1h, 4h, 4d
     * limit	false	integer	Limit for data size per page. [1, 500]. Default: 50
     */
    @GET("/v5/market/account-ratio")
    Call<Object> getMarketAccountRatio(@Query("category") String category,
                                       @Query("symbol") String symbol,
                                       @Query("period") String baseCoin,
                                       @Query("limit") Integer limit);

    // Trade

    /**
     * Get Order History
     * Query order history. As order creation/cancellation is asynchronous, the data returned from this endpoint may delay. If you want to get real-time order information, you could query this endpoint or rely on the websocket stream (recommended).
     * <p>
     * Unified account covers: Spot / USDT perpetual / USDC contract / Inverse contract / Options
     * Classic account covers: Spot / USDT perpetual / Inverse contract
     * <p>
     * INFO
     * The orders in the last 7 days: supports querying all statuses
     * The orders beyond 7 days: supports querying filled orders
     * You can query by symbol, baseCoin, orderId and orderLinkId, and if you pass multiple params, the system will process them according to this priority: orderId > orderLinkId > symbol > baseCoin.
     * TIP
     * Classic account spot can get final status orders only
     * <a href="https://bybit-exchange.github.io/docs/v5/order/order-list">...</a>
     *
     * @param category
     * @param symbol
     * @param baseCoin
     * @param settleCoin
     * @param orderId
     * @param orderLinkId
     * @param orderFilter
     * @param orderStatus
     * @param startTime
     * @param endTime
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v5/order/history")
    Call<Object> getHistoryOrderResult(@Query("category") String category,
                                       @Query("symbol") String symbol,
                                       @Query("baseCoin") String baseCoin,
                                       @Query("settleCoin") String settleCoin,
                                       @Query("orderId") String orderId,
                                       @Query("orderLinkId") String orderLinkId,
                                       @Query("orderFilter") String orderFilter,
                                       @Query("orderStatus") OrderStatus orderStatus,
                                       @Query("startTime") Long startTime,
                                       @Query("endTime") Long endTime,
                                       @Query("limit") Integer limit,
                                       @Query("cursor") String cursor);

    /**
     * Get Borrow Quota (Spot)
     * Query the qty and amount of borrowable coins in spot account.
     *
     * Covers: Spot (Unified Account)
     *
     * HTTP Request
     * GET /v5/order/spot-borrow-check
     *
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. spot
     * symbol	true	string	Symbol name
     * side	true	string	Transaction side. Buy,Sell
     * Response Parameters
     * Parameter	Type	Comments
     * symbol	string	Symbol name
     * side	string	Side
     * maxTradeQty	string	The maximum base coin qty can be traded
     * If spot margin trade on and symbol is margin trading pair, it returns available balance + max.borrowable amount
     * Otherwise, it returns actual balance
     * maxTradeAmount	string	The maximum quote coin amount can be traded
     * If spot margin trade on and symbol is margin trading pair, it returns available balance + max.borrowable amount
     * Otherwise, it returns actual balance
     * borrowCoin	string	Borrow coin
     * <a href="https://bybit-exchange.github.io/docs/v5/order/spot-borrow-quota">...</a>
     * @param category
     * @param symbol
     * @param side
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("/v5/order/spot-borrow-check")
    Call<Object> getBorrowQuota(@Query("category") String category,
                                @Query("symbol") String symbol,
                                @Query("side") String side);

    /**
     * Set Disconnect Cancel All
     * Covers: Option (Unified Account)
     *
     * INFO
     * What is Disconnection Protect (DCP)?
     * Based on the websocket private connection and heartbeat mechanism, Bybit provides disconnection protection function. The timing starts from the first disconnection. If the Bybit server does not receive the reconnection from the client for more than 10 (default) seconds and resumes the heartbeat "ping", then the client is in the state of "disconnection protect", all active option orders of the client will be cancelled automatically. If within 10 seconds, the client reconnects and resumes the heartbeat "ping", the timing will be reset and restarted at the next disconnection.
     *
     * How to enable DCP
     * If you need to turn it on/off, you can contact your client manager for consultation and application. The default time window is 10 seconds.
     *
     * Applicable
     * Effective for options only.
     *
     * TIP
     * After the request is successfully sent, the system needs a certain time to take effect. It is recommended to query or set again after 10 seconds
     *
     * You can use this endpoint to get your current DCP configuration.
     * Your private websocket connection must subscribe "dcp" topic in order to trigger DCP successfully
     * <a href="https://bybit-exchange.github.io/docs/v5/order/dcp">...</a>
     * @param timeWindow
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/order/disconnected-cancel-all")
    Call<Object> setDisconnectCancelAllTime(@Query("timeWindow") Integer timeWindow);

    /**
     * Get Open Orders
     * Query unfilled or partially filled orders in real-time. To query older order records, please use the order history interface.
     * <p>
     * Unified account covers: Spot / USDT perpetual / USDC contract / Inverse contract / Options
     * Classic account covers: Spot / USDT perpetual / Inverse contract
     * <p>
     * TIP
     * It also supports querying filled, cancelled, and rejected orders which occurred in last 10 minutes (check the openOnly param). At most, 500 orders will be returned.
     * You can query by symbol, baseCoin, orderId and orderLinkId, and if you pass multiple params, the system will process them according to this priority: orderId > orderLinkId > symbol > baseCoin.
     * The records are sorted by the createdTime from newest to oldest.
     * INFO
     * Classic account spot trade can return open orders only
     *
     * @param category
     * @param symbol
     * @param baseCoin
     * @param settleCoin
     * @param orderId
     * @param orderLinkId
     * @param openOnly
     * @param orderFilter
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/order/realtime")
    Call<Object> getOpenOrders(@Query("category") String category,
                               @Query("symbol") String symbol,
                               @Query("baseCoin") String baseCoin,
                               @Query("settleCoin") String settleCoin,
                               @Query("orderId") String orderId,
                               @Query("orderLinkId") String orderLinkId,
                               @Query("openOnly") Integer openOnly,
                               @Query("orderFilter") String orderFilter,
                               @Query("limit") Integer limit,
                               @Query("cursor") String cursor);

    /**
     * Place Order
     * This endpoint supports to create the order for spot, spot margin, USDT perpetual, USDC perpetual, USDC futures, inverse futures and options.
     * <p>
     * Unified account covers: Spot / USDT perpetual / USDC contract / Inverse contract / Options
     * Classic account covers: Spot / USDT perpetual / Inverse contract
     * <p>
     * INFO
     * Supported order type (orderType):
     * Limit order: orderType=Limit, it is necessary to specify order qty and price.
     * <p>
     * Market order: orderType=Market, execute at the best price in the Bybit market until the transaction is completed. When selecting a market order, the `price` is empty. In the futures trading system, in order to protect the serious slippage of the market order, the Bybit trading system will convert the market order into a limit order for matching. will be cancelled. The slippage threshold refers to the percentage that the order price deviates from the latest transaction price. The current threshold is set to 3% for BTC contracts and 5% for other contracts.
     * Supported timeInForce strategy:
     * GTC
     * IOC
     * FOK
     * PostOnly: If the order would be filled immediately when submitted, it will be cancelled. The purpose of this is to protect your order during the submission process. If the matching system cannot entrust the order to the order book due to price changes on the market, it will be cancelled. For the PostOnly order type, the quantity that can be submitted in a single order is more than other types of orders, please refer to the parameter lotSizeFilter > postOnlyMaxOrderQty in the instruments-info endpoint.
     * <p>
     * How to create conditional order:
     * When submitting an order, if triggerPrice is set, the order will be automatically converted into a conditional order. In addition, the conditional order does not occupy the margin. If the margin is insufficient after the conditional order is triggered, the order will be cancelled.
     * <p>
     * Take profit / Stop loss: You can set TP/SL while placing orders. Besides, you could modify the position's TP/SL.
     * <p>
     * Order quantity: The quantity of perpetual contracts you are going to buy/sell. For the order quantity, Bybit only supports positive number at present.
     * <p>
     * Order price: Place a limit order, this parameter is required. If you have position, the price should be higher than the liquidation price. For the minimum unit of the price change, please refer to the priceFilter > tickSize field in the instruments-info endpoint.
     * <p>
     * orderLinkId: You can customize the active order ID. We can link this ID to the order ID in the system. Once the active order is successfully created, we will send the unique order ID in the system to you. Then, you can use this order ID to cancel active orders, and if both orderId and orderLinkId are entered in the parameter input, Bybit will prioritize the orderId to process the corresponding order. Meanwhile, your customized order ID should be no longer than 36 characters and should be unique.
     * <p>
     * Open orders up limit:
     * Future: Each account can hold a maximum of 500 active orders simultaneously. This is contract-specific, so the following situation is allowed: the same account can hold 300 BTCUSD active orders and 280 ETHUSD active orders at the same time. For conditional orders, each account can hold a maximum of 10 active orders simultaneously. When the upper limit of orders is reached, you can still place orders with parameters of reduceOnly or closeOnTrigger.
     * Spot: 500 orders in total, including a maximum of 30 open TP/SL orders, a maximum of 30 open conditional orders
     * Option: a maximum of 100 open orders
     * <p>
     * Rate limit:
     * Please refer to rate limit table. If you need to raise the rate limit, please contact your client manager or submit an application via here
     * <p>
     * TIP
     * To margin trade on spot on a normal account, you need to go here to borrow margin first.
     * <a href="https://bybit-exchange.github.io/docs/v5/order/create-order">...</a>
     *
     * @param category
     * @param symbol
     * @param isLeverage
     * @param side
     * @param orderType
     * @param qty
     * @param price
     * @param triggerDirection
     * @param orderFilter
     * @param triggerPrice
     * @param triggerBy
     * @param orderIv
     * @param timeInForce
     * @param positionIdx
     * @param orderLinkId
     * @param takeProfit
     * @param stopLoss
     * @param tpTriggerBy
     * @param slTriggerBy
     * @param reduceOnly
     * @param closeOnTrigger
     * @param smpType
     * @param mmp
     * @param tpslMode
     * @param tpLimitPrice
     * @param slLimitPrice
     * @param tpOrderType
     * @param slOrderType
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/order/create")
    Call<Object> createOrder(@Query("category") String category,
                          @Query("symbol") String symbol,
                          @Query("isLeverage") Integer isLeverage,
                          @Query("side") String side,
                          @Query("orderType") String orderType,
                          @Query("qty") String qty,
                          @Query("price") String price,
                          @Query("triggerDirection") Integer triggerDirection,
                          @Query("orderFilter") String orderFilter,
                          @Query("triggerPrice") String triggerPrice,
                          @Query("triggerBy") String triggerBy,
                          @Query("orderIv") String orderIv,
                          @Query("timeInForce") String timeInForce,
                          @Query("positionIdx") Integer positionIdx,
                          @Query("orderLinkId") String orderLinkId,
                          @Query("takeProfit") String takeProfit,
                          @Query("stopLoss") String stopLoss,
                          @Query("tpTriggerBy") String tpTriggerBy,
                          @Query("slTriggerBy") String slTriggerBy,
                          @Query("reduceOnly") Boolean reduceOnly,
                          @Query("closeOnTrigger") Boolean closeOnTrigger,
                          @Query("smpType") String smpType,
                          @Query("mmp") Boolean mmp,
                          @Query("tpslMode") String tpslMode,
                          @Query("tpLimitPrice") String tpLimitPrice,
                          @Query("slLimitPrice") String slLimitPrice,
                          @Query("tpOrderType") String tpOrderType,
                          @Query("slOrderType") String slOrderType);

    /**
     * Batch Place Order
     * Covers: Option (UTA, UTA Pro) / USDT Perpetual, UDSC Perpetual, USDC Futures (UTA Pro)
     *
     * TIP
     * This endpoint allows you to place more than one order in a single request.
     *
     * Make sure you have sufficient funds in your account when placing an order. Once an order is placed, according to the funds required by the order, the funds in your account will be frozen by the corresponding amount during the life cycle of the order.
     * A maximum of 20 orders (option) & 10 orders (linear) can be placed per request. The returned data list is divided into two lists. The first list indicates whether or not the order creation was successful and the second list details the created order information. The structure of the two lists are completely consistent.
     * INFO
     * Check the rate limit instruction when category=linear here
     * <a href="https://bybit-exchange.github.io/docs/v5/order/batch-place">...</a>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear, option
     * request	true	array	Object
     * > symbol	true	string	Symbol name
     * > side	true	string	Buy, Sell
     * > orderType	true	string	Market, Limit
     * > qty	true	string	Order quantity
     * In particular, for linear, if you pass qty="0", you can close the whole position of current symbol
     * > price	false	string	Order price
     * Market order will ignore this field
     * Please check the min price and price precision from instrument info endpoint
     * If you have position, price needs to be better than liquidation price
     * > triggerDirection	false	integer	Conditional order param. Used to identify the expected direction of the conditional order.
     * 1: triggered when market price rises to triggerPrice
     * 2: triggered when market price falls to triggerPrice
     * Valid for linear
     * > triggerPrice	false	string
     * For futures, it is the conditional order trigger price. If you expect the price to rise to trigger your conditional order, make sure:
     * triggerPrice > market price
     * Else, triggerPrice < market price
     * > triggerBy	false	string	Conditional order param. Trigger price type. LastPrice, IndexPrice, MarkPrice
     * > orderIv	false	string	Implied volatility. option only. Pass the real value, e.g for 10%, 0.1 should be passed. orderIv has a higher priority when price is passed as well
     * > timeInForce	false	string	Time in force
     * Market order will use IOC directly
     * If not passed, GTC is used by default
     * > positionIdx	false	integer	Used to identify positions in different position modes. Under hedge-mode, this param is required (USDT perps have hedge mode)
     * 0: one-way mode
     * 1: hedge-mode Buy side
     * 2: hedge-mode Sell side
     * > orderLinkId	false	string	User customised order ID. A max of 36 characters. Combinations of numbers, letters (upper and lower cases), dashes, and underscores are supported.
     * Futures & Perps: orderLinkId rules:
     * optional param
     * always unique
     * option orderLinkId rules:
     * required param
     * always unique
     * > takeProfit	false	string	Take profit price, valid for linear
     * > stopLoss	false	string	Stop loss price, valid for linear
     * > tpTriggerBy	false	string	The price type to trigger take profit. MarkPrice, IndexPrice, default: LastPrice.
     * Valid for linear
     * > slTriggerBy	false	string	The price type to trigger stop loss. MarkPrice, IndexPrice, default: LastPrice
     * Valid for linear
     * > reduceOnly	false	boolean	What is a reduce-only order? true means your position can only reduce in size if this order is triggered.
     * You must specify it as true when you are about to close/reduce the position
     * When reduceOnly is true, take profit/stop loss cannot be set
     * Valid for linear, & option
     * > closeOnTrigger	false	boolean	What is a close on trigger order? For a closing order. It can only reduce your position, not increase it. If the account has insufficient available balance when the closing order is triggered, then other active orders of similar contracts will be cancelled or reduced. It can be used to ensure your stop loss reduces your position regardless of current available margin.
     * Valid for linear
     * > smpType	false	string	Smp execution type. What is SMP?
     * > mmp	false	boolean	Market maker protection. option only. true means set the order as a market maker protection order. What is mmp?
     * > tpslMode	false	string	TP/SL mode
     * Full: entire position for TP/SL. Then, tpOrderType or slOrderType must be Market
     * Partial: partial position tp/sl. Limit TP/SL order are supported. Note: When create limit tp/sl, tpslMode is required and it must be Partial
     * Valid for linear
     * > tpLimitPrice	false	string	The limit order price when take profit price is triggered. Only works when tpslMode=Partial and tpOrderType=Limit
     * Valid for linear
     * > slLimitPrice	false	string	The limit order price when stop loss price is triggered. Only works when tpslMode=Partial and slOrderType=Limit
     * Valid for linear
     * > tpOrderType	false	string	The order type when take profit is triggered. Market(default), Limit. For tpslMode=Full, it only supports tpOrderType=Market
     * Valid for linear
     * > slOrderType	false	string	The order type when stop loss is triggered. Market(default), Limit. For tpslMode=Full, it only supports slOrderType=Market
     * Valid for linear
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear, option
     * request	true	array	Object
     * > symbol	true	string	Symbol name
     * > side	true	string	Buy, Sell
     * > orderType	true	string	Market, Limit
     * > qty	true	string	Order quantity
     * In particular, for linear, if you pass qty="0", you can close the whole position of current symbol
     * > price	false	string	Order price
     * Market order will ignore this field
     * Please check the min price and price precision from instrument info endpoint
     * If you have position, price needs to be better than liquidation price
     * > triggerDirection	false	integer	Conditional order param. Used to identify the expected direction of the conditional order.
     * 1: triggered when market price rises to triggerPrice
     * 2: triggered when market price falls to triggerPrice
     * Valid for linear
     * > triggerPrice	false	string
     * For futures, it is the conditional order trigger price. If you expect the price to rise to trigger your conditional order, make sure:
     * triggerPrice > market price
     * Else, triggerPrice < market price
     * > triggerBy	false	string	Conditional order param. Trigger price type. LastPrice, IndexPrice, MarkPrice
     * > orderIv	false	string	Implied volatility. option only. Pass the real value, e.g for 10%, 0.1 should be passed. orderIv has a higher priority when price is passed as well
     * > timeInForce	false	string	Time in force
     * Market order will use IOC directly
     * If not passed, GTC is used by default
     * > positionIdx	false	integer	Used to identify positions in different position modes. Under hedge-mode, this param is required (USDT perps have hedge mode)
     * 0: one-way mode
     * 1: hedge-mode Buy side
     * 2: hedge-mode Sell side
     * > orderLinkId	false	string	User customised order ID. A max of 36 characters. Combinations of numbers, letters (upper and lower cases), dashes, and underscores are supported.
     * Futures & Perps: orderLinkId rules:
     * optional param
     * always unique
     * option orderLinkId rules:
     * required param
     * always unique
     * > takeProfit	false	string	Take profit price, valid for linear
     * > stopLoss	false	string	Stop loss price, valid for linear
     * > tpTriggerBy	false	string	The price type to trigger take profit. MarkPrice, IndexPrice, default: LastPrice.
     * Valid for linear
     * > slTriggerBy	false	string	The price type to trigger stop loss. MarkPrice, IndexPrice, default: LastPrice
     * Valid for linear
     * > reduceOnly	false	boolean	What is a reduce-only order? true means your position can only reduce in size if this order is triggered.
     * You must specify it as true when you are about to close/reduce the position
     * When reduceOnly is true, take profit/stop loss cannot be set
     * Valid for linear, & option
     * > closeOnTrigger	false	boolean	What is a close on trigger order? For a closing order. It can only reduce your position, not increase it. If the account has insufficient available balance when the closing order is triggered, then other active orders of similar contracts will be cancelled or reduced. It can be used to ensure your stop loss reduces your position regardless of current available margin.
     * Valid for linear
     * > smpType	false	string	Smp execution type. What is SMP?
     * > mmp	false	boolean	Market maker protection. option only. true means set the order as a market maker protection order. What is mmp?
     * > tpslMode	false	string	TP/SL mode
     * Full: entire position for TP/SL. Then, tpOrderType or slOrderType must be Market
     * Partial: partial position tp/sl. Limit TP/SL order are supported. Note: When create limit tp/sl, tpslMode is required and it must be Partial
     * Valid for linear
     * > tpLimitPrice	false	string	The limit order price when take profit price is triggered. Only works when tpslMode=Partial and tpOrderType=Limit
     * Valid for linear
     * > slLimitPrice	false	string	The limit order price when stop loss price is triggered. Only works when tpslMode=Partial and slOrderType=Limit
     * Valid for linear
     * > tpOrderType	false	string	The order type when take profit is triggered. Market(default), Limit. For tpslMode=Full, it only supports tpOrderType=Market
     * Valid for linear
     * > slOrderType	false	string	The order type when stop loss is triggered. Market(default), Limit. For tpslMode=Full, it only supports slOrderType=Market
     * Valid for linear
     * @param batchOrderRequest
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/order/create-batch")
    Call<Object> createBatchOrder(@Body BatchOrderRequest batchOrderRequest);

    /**
     * Cancel Order
     * Unified account covers: Spot / USDT perpetual / USDC contract / Inverse contract / Options
     * Classic account covers: Spot / USDT perpetual / Inverse contract
     * <p>
     * IMPORTANT
     * You must specify orderId or orderLinkId to cancel the order.
     * If orderId and orderLinkId do not match, the system will process orderId first.
     * You can only cancel unfilled or partially filled orders.
     * <a href="https://bybit-exchange.github.io/docs/v5/order/cancel-order">...</a>
     *
     * @param category
     * @param symbol
     * @param orderId
     * @param orderLinkId
     * @param orderFilter
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/order/cancel")
    Call<Object> cancelOrder(@Query("category") String category,
                             @Query("symbol") String symbol,
                             @Query("orderId") String orderId,
                             @Query("orderLinkId") String orderLinkId,
                             @Query("orderFilter") String orderFilter);

    /**
     * Batch Cancel Order
     * This endpoint allows you to cancel more than one open order in a single request.
     * <p>
     * Covers: Option (UTA, UTA Pro) / USDT Perpetual, UDSC Perpetual, USDC Futures (UTA Pro)
     * <p>
     * IMPORTANT
     * You must specify orderId or orderLinkId.
     * If orderId and orderLinkId is not matched, the system will process orderId first.
     * You can cancel unfilled or partially filled orders.
     * A maximum of 20 orders (option) & 10 orders (linear) can be cancelled per request.
     * HTTP Request
     * POST /v5/order/cancel-batch
     * <p>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear, option
     * request	true	array	Object
     * > symbol	true	string	Symbol name
     * > orderId	false	string	Order ID. Either orderId or orderLinkId is required
     * > orderLinkId	false	string	User customised order ID. Either orderId or orderLinkId is required
     * <a href="https://bybit-exchange.github.io/docs/v5/order/batch-cancel">...</a>
     * @param batchOrderRequest
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/order/cancel-batch")
    Call<Object> cancelBatchOrder(@Body BatchOrderRequest batchOrderRequest);

    /**
     * Cancel All Orders
     * Cancel all open orders
     * <p>
     * Unified account covers: Spot / USDT perpetual / USDC contract / Inverse contract / Options
     * Classic account covers: Spot / USDT perpetual / Inverse contract
     * <p>
     * INFO
     * Support cancel orders by symbol/baseCoin/settleCoin. If you pass multiple of these params, the system will process one of param, which priority is symbol > baseCoin > settleCoin.
     * NOTE: category=option, you can cancel all option open orders without passing any of those three params. However, for linear and inverse, you must specify one of those three params.
     * NOTE: category=spot, you can cancel all spot open orders (normal order by default) without passing other params.
     * HTTP Request
     * POST /v5/order/cancel-all
     * <p>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type
     * Unified account: spot, linear, inverse, option
     * Classic account: spot, linear, inverse
     * symbol	false	string	Symbol name. linear & inverse: Required if not passing baseCoin or settleCoin
     * baseCoin	false	string	Base coin
     * linear & inverse: If cancel all by baseCoin, it will cancel all linear & inverse orders. Required if not passing symbol or settleCoin
     * Classic spot: invalid
     * settleCoin	false	string	Settle coin
     * linear & inverse: Required if not passing symbol or baseCoin
     * Does not support spot
     * orderFilter	false	string
     * category=spot, you can pass Order, tpslOrder, StopOrder. If not passed, Order by default
     * category=linear or inverse, you can pass Order, StopOrder. If not passed, all kinds of orders will be cancelled, like active order, conditional order, TP/SL order and trailing stop order
     * category=option, you can pass Order. No matter it is passed or not, always cancel all orders
     * stopOrderType	false	string	Stop order type, Stop
     * Only used for category=linear or inverse and orderFilter=StopOrder,you can cancel conditional orders except TP/SL order and Trailing stop orders with this param
     * <a href="https://bybit-exchange.github.io/docs/v5/order/cancel-all">...</a>
     *
     * @param category
     * @param symbol
     * @param baseCoin
     * @param settleCoin
     * @param orderFilter
     * @param stopOrderType
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/order/cancel-all")
    Call<Object> cancelAllOrder(@Query("category") String category,
                                @Query("symbol") String symbol,
                                @Query("baseCoin") String baseCoin,
                                @Query("settleCoin") String settleCoin,
                                @Query("orderFilter") String orderFilter,
                                @Query("stopOrderType") String stopOrderType);

    /**
     * Amend Order
     * Unified account covers: USDT perpetual / USDC contract / Inverse contract / Option
     * Classic account covers: USDT perpetual / Inverse contract
     * <p>
     * IMPORTANT
     * You can only modify unfilled or partially filled orders.
     * <p>
     * HTTP Request
     * POST /v5/order/amend
     * <p>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type
     * Unified account: linear, inverse, option
     * Classic account: linear, inverse. Please note that category is not involved with business logic
     * symbol	true	string	Symbol name
     * orderId	false	string	Order ID. Either orderId or orderLinkId is required
     * orderLinkId	false	string	User customised order ID. Either orderId or orderLinkId is required
     * orderIv	false	string	Implied volatility. option only. Pass the real value, e.g for 10%, 0.1 should be passed
     * triggerPrice	false	string	If you expect the price to rise to trigger your conditional order, make sure:
     * triggerPrice > market price
     * Else, triggerPrice < market price
     * qty	false	string	Order quantity after modification. Do not pass it if not modify the qty
     * price	false	string	Order price after modification. Do not pass it if not modify the price
     * takeProfit	false	string	Take profit price after modification. If pass "0", it means cancel the existing take profit of the order. Do not pass it if you do not want to modify the take profit
     * stopLoss	false	string	Stop loss price after modification. If pass "0", it means cancel the existing stop loss of the order. Do not pass it if you do not want to modify the stop loss
     * tpTriggerBy	false	string	The price type to trigger take profit. When set a take profit, this param is required if no initial value for the order
     * slTriggerBy	false	string	The price type to trigger stop loss. When set a take profit, this param is required if no initial value for the order
     * triggerBy	false	string	Trigger price type
     * tpLimitPrice	false	string	Limit order price when take profit is triggered. Only working when original order sets partial limit tp/sl
     * slLimitPrice	false	string	Limit order price when stop loss is triggered. Only working when original order sets partial limit tp/sl
     * <a href="https://bybit-exchange.github.io/docs/v5/order/amend-order">...</a>
     *
     * @param category
     * @param symbol
     * @param orderId
     * @param orderLinkId
     * @param orderIv
     * @param triggerPrice
     * @param qty
     * @param price
     * @param takeProfit
     * @param stopLoss
     * @param tpTriggerBy
     * @param slTriggerBy
     * @param triggerBy
     * @param tpLimitPrice
     * @param slLimitPrice
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/order/amend")
    Call<Object> amendOrder(@Query("category") String category,
                            @Query("symbol") String symbol,
                            @Query("orderId") String orderId,
                            @Query("orderLinkId") String orderLinkId,
                            @Query("orderIv") String orderIv,
                            @Query("triggerPrice") String triggerPrice,
                            @Query("qty") String qty,
                            @Query("price") String price,
                            @Query("takeProfit") String takeProfit,
                            @Query("stopLoss") String stopLoss,
                            @Query("tpTriggerBy") TriggerBy tpTriggerBy,
                            @Query("slTriggerBy") TriggerBy slTriggerBy,
                            @Query("triggerBy") TriggerBy triggerBy,
                            @Query("tpLimitPrice") String tpLimitPrice,
                            @Query("slLimitPrice") String slLimitPrice);

    /**
     * Batch Amend Order
     * Covers: Option (UTA, UTA Pro) / USDT Perpetual, UDSC Perpetual, USDC Futures (UTA Pro)
     * <p>
     * TIP
     * This endpoint allows you to amend more than one open order in a single request.
     * <p>
     * You can modify unfilled or partially filled orders. Conditional orders are not supported.
     * A maximum of 20 orders (option) & 10 orders (linear) can be amended per request.
     * HTTP Request
     * POST /v5/order/amend-batch
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/order/batch-amend">...</a>
     * <p>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * category	true	string	Product type. linear, option
     * request	true	array	Object
     * > symbol	true	string	Symbol name
     * > orderId	false	string	Order ID. Either orderId or orderLinkId is required
     * > orderLinkId	false	string	User customised order ID. Either orderId or orderLinkId is required
     * > orderIv	false	string	Implied volatility. option only. Pass the real value, e.g for 10%, 0.1 should be passed
     * > triggerPrice	false	string	If you expect the price to rise to trigger your conditional order, make sure:
     * triggerPrice > market price
     * Else, triggerPrice < market price
     * > qty	false	string	Order quantity after modification. Do not pass it if not modify the qty
     * > price	false	string	Order price after modification. Do not pass it if not modify the price
     * > takeProfit	false	string	Take profit price after modification. If pass "0", it means cancel the existing take profit of the order. Do not pass it if you do not want to modify the take profit
     * > stopLoss	false	string	Stop loss price after modification. If pass "0", it means cancel the existing stop loss of the order. Do not pass it if you do not want to modify the stop loss
     * > tpTriggerBy	false	string	The price type to trigger take profit. When set a take profit, this param is required if no initial value for the order
     * > slTriggerBy	false	string	The price type to trigger stop loss. When set a take profit, this param is required if no initial value for the order
     * > triggerBy	false	string	Trigger price type
     * > tpLimitPrice	false	string	Limit order price when take profit is triggered. Only working when original order sets partial limit tp/sl
     * > slLimitPrice	false	string	Limit order price when stop loss is triggered. Only working when original order sets partial limit tp/sl
     * @param batchOrderRequest
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/order/amend-batch")
    Call<Object> amendBatchOrder(@Body BatchOrderRequest batchOrderRequest);

    // User
    /*
    to do : modify & delete master and sub api key !! need to investigate
    * */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/user/query-api")
    Call<Object> getCurrentAPIKeyInfo();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/user/query-sub-members")
    Call<Object> getSubUIDList();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/user/get-member-type")
    Call<Object> getUIDWalletType(@Query("memberIds") String memberIds);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/user/get-member-type")
    Call<Object> getUIDWalletType();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/user/aff-customer-info")
    Call<Object> getAffiliateUserInfo(@Query("iod") String uid);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/user/create-sub-member")
    Call<Object> createSubMember(@Query("username") String username,
                                 @Query("password") String password,
                                 @Query("memberType") Integer memberType,
                                 @Query("switch") Integer switchOption,
                                 @Query("isUta") boolean isUta,
                                 @Query("note") String note);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/user/create-sub-api")
    Call<Object> createSubAPI(
            @Body ApiKeyRequest apiKeyRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/user/frozen-sub-member")
    Call<Object> freezeSubMember(
            @Body FreezeSubUIDRquest freezeSubUIDRquest);

    // Position Data endpoints
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/position/list")
    Call<Object> getPositionInfo(@Query("category") String category,
                                 @Query("symbol") String symbol,
                                 @Query("baseCoin") String baseCoin,
                                 @Query("settleCoin") String settleCoin,
                                 @Query("limit") Integer limit,
                                 @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/position/set-leverage")
    Call<Object> setPositionLeverage(
            @Body SetLeverageRequest setLeverageRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/position/switch-isolated")
    Call<Object> swithMarginRequest(
            @Body SwitchMarginRequest switchMarginRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/position/switch-mode")
    Call<Object> switchPositionMode(
            @Body SwitchPositionModeRequest switchPositionModeRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/position/set-tpsl-mode")
    Call<Object> setTpslMode(
            @Body SetTpSlModeRequest setTpSlModeRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/position/set-risk-limit")
    Call<Object> setRiskLimit(
            @Body SetRiskLimitRequest setRiskLimitRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/position/trading-stop")
    Call<Object> setTradingStop(
            @Body TradingStopRequest tradingStopRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/position/set-auto-add-margin")
    Call<Object> setAutoAddMargin(
            @Body SetAutoAddMarginRequest setAutoAddMarginRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/position/add-margin")
    Call<Object> modifyPositionMargin(
            @Body ModifyMarginRequest modifyMarginRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/execution/list")
    Call<Object> getExecutionList(@Query("category") String category,
                                  @Query("symbol") String symbol,
                                  @Query("orderId") String orderId,
                                  @Query("orderLinkId") String orderLinkId,
                                  @Query("baseCoin") String baseCoin,
                                  @Query("startTime") Long startTime,
                                  @Query("endTime") Long endTime,
                                  @Query("execType") String execType,
                                  @Query("limit") Integer limit,
                                  @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/position/closed-pnl")
    Call<Object> getClosePnlList(@Query("category") String category,
                                 @Query("symbol") String symbol,
                                 @Query("startTime") Long startTime,
                                 @Query("endTime") Long endTime,
                                 @Query("limit") Integer limit,
                                 @Query("cursor") String cursor);

    // Pre upgrade data endpoints
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/pre-upgrade/position/closed-pnl")
    Call<Object> getPreUpgradeClosePnl(@Query("category") String category,
                                       @Query("symbol") String symbol,
                                       @Query("startTime") Long startTime,
                                       @Query("endTime") Long endTime,
                                       @Query("limit") Integer limit,
                                       @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/pre-upgrade/asset/delivery-record")
    Call<Object> getPreUpgradeOptionDelivery(@Query("category") String category,
                                             @Query("symbol") String symbol,
                                             @Query("expDate") String expDate,
                                             @Query("limit") Integer limit,
                                             @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/pre-upgrade/order/history")
    Call<Object> getPreUpgradeOrderHistory(@Query("category") String category,
                                           @Query("symbol") String symbol,
                                           @Query("baseCoin") String baseCoin,
                                           @Query("orderId") String orderId,
                                           @Query("orderLinkId") String orderLinkId,
                                           @Query("orderFilter") String orderFilter,
                                           @Query("orderStatus") String orderStatus,
                                           @Query("startTime") Long startTime,
                                           @Query("endTime") Long endTime,
                                           @Query("limit") Integer limit,
                                           @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/pre-upgrade/execution/list")
    Call<Object> getPreUpgradeTradeHistory(@Query("category") String category,
                                           @Query("symbol") String symbol,
                                           @Query("orderId") String orderId,
                                           @Query("orderLinkId") String orderLinkId,
                                           @Query("baseCoin") String baseCoin,
                                           @Query("startTime") Long startTime,
                                           @Query("endTime") Long endTime,
                                           @Query("execType") String execType,
                                           @Query("limit") Integer limit,
                                           @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/pre-upgrade/account/transaction-log")
    Call<Object> getPreUpgradeTransaction(@Query("category") String category,
                                          @Query("baseCoin") String baseCoin,
                                          @Query("type") String type,
                                          @Query("startTime") Long startTime,
                                          @Query("endTime") Long endTime,
                                          @Query("limit") Integer limit,
                                          @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/pre-upgrade/asset/settlement-record")
    Call<Object> getPreUpgradeUsdcSettlement(@Query("category") String category,
                                             @Query("symbol") String symbol,
                                             @Query("limit") Integer limit,
                                             @Query("cursor") String cursor);

    // Account Data endpoints
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/account/wallet-balance")
    Call<Object> getWalletBalance(@Query("accountType") String accountType,
                                  @Query("coin") String coin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/account/upgrade-to-uta")
    Call<Object> upgradeAccountToUTA();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/account/borrow-history")
    Call<Object> getAccountBorrowHistory(@Query("currency") String currency,
                                         @Query("startTime") Long startTime,
                                         @Query("endTime") Long endTime,
                                         @Query("limit") Integer limit,
                                         @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/account/set-collateral-switch")
    Call<Object> setAccountCollateralCoin(@Body SetCollateralCoinRequest setCollateralCoinRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/account/collateral-info")
    Call<Object> getAccountCollateralInfo();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/account/collateral-info")
    Call<Object> getAccountCollateralInfo(@Query("currency") String currency);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/coin-greeks")
    Call<Object> getAccountCoinGeeks();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/coin-greeks")
    Call<Object> getAccountCoinGeeks(@Query("baseCoin") String baseCoin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/account/fee-rate")
    Call<Object> getAccountFreeRate(@Query("category") String category,
                                    @Query("symbol") String symbol,
                                    @Query("baseCoin") String baseCoin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/account/info")
    Call<Object> getAccountInfo();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/account/transaction-log")
    Call<Object> getTransactionLog(@Query("accountType") String accountType,
                                   @Query("category") String category,
                                   @Query("currency") String currency,
                                   @Query("baseCoin") String baseCoin,
                                   @Query("type") String type,
                                   @Query("startTime") Long startTime,
                                   @Query("endTime") Long endTime,
                                   @Query("limit") Integer limit,
                                   @Query("cursor") String cursor);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/account/set-margin-mode")
    Call<Object> setAccountMarginMode(@Query("setMarginMode") String setMarginMode); // ISOLATED_MARGIN, REGULAR_MARGIN(i.e. Cross margin), PORTFOLIO_MARGIN

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/account/mmp-modify")
    Call<Object> modifyAccountMMP(@Body SetMMPRequest setMMPRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/account/mmp-reset")
    Call<Object> resetAccountMMP(@Query("baseCoin") String baseCoin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/account/mmp-state")
    Call<Object> getAccountMMPState(@Query("baseCoin") String baseCoin);

    // Institution Endpoints

    /**
     * Get Product Info
     * TIP
     * This endpoint can be queried without api key and secret, then it returns public product data
     * If your uid is bound with OTC loan product, then you can get your private product data by calling the endpoint with api key and secret
     * If your uid is not bound with OTC loan product but api key and secret are also passed, it will return public data only
     * HTTP Request
     * GET /v5/ins-loan/product-infos
     * <p>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * productId	false	string	Product Id. If not passed, then return all products info
     * <a href="https://bybit-exchange.github.io/docs/v5/otc/margin-product-info">...</a>
     *
     * @param productId
     * @return
     */
    @GET("/v5/ins-loan/product-infos")
    Call<Object> getInsProductInfo(@Query("productId") String productId);

    @GET("/v5/ins-loan/product-infos")
    Call<Object> getInsProductInfo();

    /**
     * Get Margin Coin Info
     * TIP
     * This endpoint can be queried without api key and secret, then it returns public margin data
     * If your uid is bound with OTC loan product, then you can get your private margin data by calling the endpoint with api key and secret
     * If your uid is not bound with OTC loan product but api key and secret are also passed, it will return public data only
     * Request Parameters
     * Parameter	Required	Type	Comments
     * productId	false	string	ProductId. If not passed, then return all product margin coin. For spot, it returns coin that convertRation greater than 0.
     * <a href="https://bybit-exchange.github.io/docs/v5/otc/margin-coin-convert-info">...</a>
     *
     * @param productId
     * @return
     */
    @GET("/v5/ins-loan/ensure-tokens-convert")
    Call<Object> getInsMarginCoinInfo(@Query("productId") String productId);

    @GET("/v5/ins-loan/ensure-tokens-convert")
    Call<Object> getInsMarginCoinInfo();

    /**
     * Get Loan Orders
     * Get loan orders information
     * <p>
     * TIP
     * Get the past 2 years data by default
     * Get up to the past 2 years of data
     * Request Parameters
     * Parameter	Required	Type	Comments
     * orderId	false	string	Loan order id. If not passed, then return all orders, sort by loanTime in descend
     * startTime	false	integer	The start timestamp (ms)
     * endTime	false	integer	The end timestamp (ms)
     * limit	false	integer	Limit for data size. [1, 100], Default: 10
     * <a href="https://bybit-exchange.github.io/docs/v5/otc/loan-info">...</a>
     *
     * @param productId
     * @param startTime
     * @param endTime
     * @param limit
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/ins-loan/loan-order")
    Call<Object> getInsLoanOrders(@Query("productId") String productId,
                                  @Query("startTime") Long startTime,
                                  @Query("endTime") Long endTime,
                                  @Query("limit") Integer limit);

    /**
     * Get Repay Orders
     * Get repaid order information
     * <p>
     * TIP
     * Get the past 2 years data by default
     * Get up to the past 2 years of data
     * HTTP Request
     * GET /v5/ins-loan/repaid-history
     * <p>
     * Request Parameters
     * Parameter	Required	Type	Comments
     * startTime	false	integer	The start timestamp (ms)
     * endTime	false	integer	The end timestamp (ms)
     * limit	false	integer	Limit for data size. [1, 100]. Default: 100
     * <a href="https://bybit-exchange.github.io/docs/v5/otc/repay-info">...</a>
     *
     * @param startTime
     * @param endTime
     * @param limit
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/ins-loan/repaid-history")
    Call<Object> getInsRepayOrders(@Query("startTime") Long startTime,
                                   @Query("endTime") Long endTime,
                                   @Query("limit") Integer limit);

    /**
     * Get LTV
     * <p>
     * HTTP Request
     * GET /v5/ins-loan/ltv-convert
     * <a href="https://bybit-exchange.github.io/docs/v5/otc/ltv-convert">...</a>
     *
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/ins-loan/ltv-convert")
    Call<Object> getInsLoanToValue();

    // Spot Data endpoints

    // Spot Leverage
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-lever-token/info")
    Call<Object> getSpotLeverageTokenInfo(@Query("ltCoin") String ltCoin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-lever-token/info")
    Call<Object> getSpotLeverageTokenInfo();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-lever-token/reference")
    Call<Object> getSpotLeverageTokenMarket(@Query("ltCoin") String ltCoin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-lever-token/reference")
    Call<Object> getSpotLeverageTokenMarket();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/spot-lever-token/purchase")
    Call<Object> purchaseSpotLeverageToken(@Body SpotLeverageTokenRequest spotLeverageTokenRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/spot-lever-token/redeem")
    Call<Object> redeemSpotLeverageToken(@Body SpotLeverageTokenRequest spotLeverageTokenRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-lever-token/order-record")
    Call<Object> getSpotLeverageRecords(@Query("ltCoin") String ltCoin,
                                        @Query("orderId") String orderId,
                                        @Query("startTime") Long startTime,
                                        @Query("endTime") Long endTime,
                                        @Query("limit") Integer limit,
                                        @Query("ltOrderType") Integer ltOrderType,
                                        @Query("serialNo") String serialNo);

    // Spot Margin UTA
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-margin-trade/data")
    Call<Object> getUtaVipSpotMarginTradeData(@Query("vipLevel") String vipLevel,
                                              @Query("currency") String currency);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/spot-margin-trade/switch-mode")
    Call<Object> setUTASpotMarginTrade(@Body String spotMarginMode);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/spot-margin-trade/set-leverage")
    Call<Object> setUTASpotMarginTradeLeverage(@Body String leverage);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-margin-trade/state")
    Call<Object> getUTASpotMarginTradeLeverageState();

    // Spot Margin Normal
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/data")
    Call<Object> getNormalVipSpotMarginTradeData(@Query("vipLevel") String vipLevel,
                                                 @Query("currency") String currency);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/pledge-token")
    Call<Object> getNormalSpotMarginTradeCoinInfo(@Query("coin") String coin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/pledge-token")
    Call<Object> getNormalSpotMarginTradeCoinInfo();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/borrow-token")
    Call<Object> getNormalSpotMarginTradeBorrowCoinInfo(@Query("coin") String coin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/borrow-token")
    Call<Object> getNormalSpotMarginTradeBorrowCoinInfo();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/loan-info")
    Call<Object> getNormalSpotMarginTradeInterestQuota(@Query("coin") String coin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/account")
    Call<Object> getNormalSpotMarginTradeAccountInfo();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/spot-cross-margin-trade/switch")
    Call<Object> getNormalSpotToggleMarginTrade(@Body int switchStatus);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/spot-cross-margin-trade/loan")
    Call<Object> loanNormalSpotMarginTrade(@Body SpotMarginTradeBorrowRequest spotMarginTradeBorrowRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/spot-cross-margin-trade/repay")
    Call<Object> repayNormalSpotMarginTrade(@Body SpotMarginTradeRePayRequest spotMarginTradeRePayRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/orders")
    Call<Object> getNormalMarginTradeBorrowOrders(@Query("startTime") Long startTime,
                                                  @Query("endTime") Long endTime,
                                                  @Query("coin") String coin,
                                                  @Query("status") Integer status,
                                                  @Query("limit") Integer limit);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/spot-cross-margin-trade/repay-history")
    Call<Object> getNormalMarginTradeRepayOrders(@Query("startTime") Long startTime,
                                                 @Query("endTime") Long endTime,
                                                 @Query("coin") String coin,
                                                 @Query("limit") Integer limit);

    // Broker Endpoints
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/broker/earning-record")
    Call<Object> getBrokerEarningData(@Query("bizType") String bizType,
                                      @Query("startTime") Long startTime,
                                      @Query("endTime") Long endTime,
                                      @Query("limit") Integer limit,
                                      @Query("cursor") String cursor);

    // C2C Endpoints
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/lending/info")
    Call<Object> getC2CLendingCoinInfo(@Query("coin") String coin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/lending/info")
    Call<Object> getC2CLendingCoinInfo();

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/lending/purchase")
    Call<Object> C2cLendingDepositFunds(@Body ClientLendingFundsRequest depsoitFundRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/lending/redeem")
    Call<Object> C2cLendingRedeemFunds(@Body ClientLendingFundsRequest depsoitFundRequest);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/lending/history-order")
    Call<Object> getC2cOrdersRecords(@Query("coin") String coin,
                                     @Query("orderId") String orderId,
                                     @Query("startTime") Long startTime,
                                     @Query("endTime") Long endTime,
                                     @Query("limit") Integer limit,
                                     @Query("orderType") String orderType);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/lending/account")
    Call<Object> getC2CLendingAccountInfo(@Query("coin") String coin);

    // Asset Endpoints

    /**
     * Get Coin Exchange Records
     * Query the coin exchange records.
     * <p>
     * INFO
     * This endpoint currently is not available to get data after 12 Mar 2023. We will make it fully available later.
     * <p>
     * CAUTION
     * You may have a long delay of this endpoint.
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/exchange">...</a>
     *
     * @param fromCoin
     * @param toCoin
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/exchange/order-record")
    Call<Object> getAssetCoinExchangeRecords(@Query("fromCoin") String fromCoin,
                                             @Query("toCoin") String toCoin,
                                             @Query("limit") Integer limit,
                                             @Query("cursor") String cursor);

    /**
     * Get Delivery Record
     * Query delivery records of USDC futures and Options, sorted by deliveryTime in descending order
     * <p>
     * Unified account covers: USDC futures / Option
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/delivery">...</a>
     *
     * @param category
     * @param symbol
     * @param expDate
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/delivery-record")
    Call<Object> getAssetDeliveryRecords(@Query("category") String category,
                                         @Query("symbol") String symbol,
                                         @Query("expDate") String expDate,
                                         @Query("limit") Integer limit,
                                         @Query("cursor") String cursor);

    /**
     * Get USDC Session Settlement
     * Query session settlement records of USDC perpetual and futures
     * <p>
     * Unified account covers: USDC perpetual / USDC futures
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/settle">...</a>">...</a>
     *
     * @param category
     * @param symbol
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/settlement-record")
    Call<Object> getAssetUSDCSettlementRecords(@Query("category") String category,
                                               @Query("symbol") String symbol,
                                               @Query("limit") Integer limit,
                                               @Query("cursor") String cursor);

    /**
     * Get Asset Info
     * Query asset information
     * <p>
     * INFO
     * For now, it can query SPOT only.
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/asset-info">...</a>
     *
     * @param accountType
     * @param coin
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/transfer/query-asset-info")
    Call<Object> getAssetInfo(@Query("accountType") String accountType,
                              @Query("coin") String coin);


    /**
     * Get All Coins Balance
     * You could get all coin balance of all account types under the master account, and sub account.
     * <p>
     * IMPORTANT
     * It is not allowed to get master account coin balance via sub account api key.
     * <p>
     * <a href="  * https://bybit-exchange.github.io/docs/v5/asset/all-bal">...</a>ance
     *
     * @param accountType
     * @param memberId
     * @param coin
     * @param withBonus
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/transfer/query-account-coins-balance")
    Call<Object> getAssetAllCoinsBalance(@Query("accountType") String accountType,
                                         @Query("memberId") String memberId,
                                         @Query("coin") String coin,
                                         @Query("withBonus") String withBonus);

    /**
     * Get Single Coin Balance
     * Query the balance of a specific coin in a specific account type. Supports querying sub UID's balance. Also, you can check the transferable amount from master to sub account, sub to master account or sub to sub account, especially for user who has INS loan.
     * <p>
     * INFO
     * Sub account cannot query master account balance
     * Sub account can only check its own balance
     * Master account can check its own and its sub uids balance
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/account-coin-balance">...</a>
     *
     * @param accountType
     * @param toAccountType
     * @param memberId
     * @param toMemberId
     * @param coin
     * @param withBonus
     * @param withTransferSafeAmount
     * @param withLtvTransferSafeAmount
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/transfer/query-account-coin-balance")
    Call<Object> getAssetSingleCoinBalance(@Query("accountType") String accountType,
                                           @Query("toAccountType") String toAccountType,
                                           @Query("memberId") String memberId,
                                           @Query("toMemberId") String toMemberId,
                                           @Query("coin") String coin,
                                           @Query("withBonus") Integer withBonus,
                                           @Query("withTransferSafeAmount") Integer withTransferSafeAmount,
                                           @Query("withLtvTransferSafeAmount") Integer withLtvTransferSafeAmount);

    /**
     * Get Transferable Coins
     * Query the transferable coin list between each account type
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/transferable-coin">...</a>
     *
     * @param fromAccountType
     * @param toAccountType
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/transfer/query-transfer-coin-list")
    Call<Object> getAssetTransferableCoins(@Query("fromAccountType") String fromAccountType, @Query("toAccountType") String toAccountType);

    /**
     * Get Internal Transfer Records
     * Query the internal transfer records between different account types under the same UID.
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/inter-transfer-list">...</a>
     * HTTP Request
     *
     * @param assetInternalTransferRequest
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/asset/transfer/inter-transfer")
    Call<Object> createAssetInternalTransfer(@Body AssetInternalTransferRequest assetInternalTransferRequest);

    /**
     * Get Sub UID
     * Query the sub UIDs under a main UID
     * <p>
     * CAUTION
     * Can query by the master UID's api key only
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/sub-uid-list">...</a>
     *
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/transfer/query-sub-member-list")
    Call<Object> getAssetTransferSubUidList();

    /**
     * Create Universal Transfer
     * Transfer between sub-sub or main-sub.
     * <p>
     * CAUTION
     * Can use master or sub acct api key to request
     * To use sub acct api key, it must have "SubMemberTransferList" permission
     * When use sub acct api key, it can only transfer to main account
     * If you encounter errorCode: 131228 and msg: your balance is not enough, please go to Get Single Coin Balance to check transfer safe amount.
     * You can not transfer between the same UID
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/unitransfer">...</a>
     *
     * @param assetUniversalTransferRequest
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/asset/transfer/universal-transfer")
    Call<Object> createAssetUniversalTransfer(@Body AssetUniversalTransferRequest assetUniversalTransferRequest);

    /**
     * Get Internal Transfer Records
     * Query the internal transfer records between different account types under the same UID.
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/inter-transfer-list">...</a>
     *
     * @param transferId
     * @param coin
     * @param status
     * @param startTime
     * @param endTime
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/transfer/query-inter-transfer-list")
    Call<Object> getAssetInternalTransferRecords(@Query("transferId") String transferId,
                                                 @Query("coin") String coin,
                                                 @Query("status") String status,
                                                 @Query("startTime") Long startTime,
                                                 @Query("endTime") Long endTime,
                                                 @Query("limit") Integer limit,
                                                 @Query("cursor") String cursor);

    /**
     * Get Universal Transfer Records
     * Query universal transfer records
     * <p>
     * TIP
     * Main acct api key or Sub acct api key are both supported
     * Main acct api key needs "SubMemberTransfer" permission
     * Sub acct api key needs "SubMemberTransferList" permission
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/unitransfer-list">...</a>
     *
     * @param transferId
     * @param coin
     * @param status
     * @param startTime
     * @param endTime
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/transfer/query-universal-transfer-list")
    Call<Object> getAssetUniversalTransferRecords(@Query("transferId") String transferId,
                                                  @Query("coin") String coin,
                                                  @Query("status") String status,
                                                  @Query("startTime") Long startTime,
                                                  @Query("endTime") Long endTime,
                                                  @Query("limit") Integer limit,
                                                  @Query("cursor") String cursor);

    /**
     * Get Allowed Deposit Coin Info
     * Query allowed deposit coin information. To find out paired chain of coin, please refer coin info api.
     * <p>
     * TIP
     * This is an endpoint that does not need authentication
     * <p>
     * <a href="  * https://bybit-exchange.github.io/docs/v5/asset/deposit-coin-">...</a>
     *
     * @param coin
     * @param chain
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/deposit/query-allowed-list")
    Call<Object> getAssetAllowedDepositCoinInfo(
            @Query("coin") String coin,
            @Query("chain") String chain,
            @Query("limit") Integer limit,
            @Query("cursor") String cursor);


    /**
     * Set Deposit Account
     * Set auto transfer account after deposit. The same function as the setting for Deposit on web GUI
     * <p>
     * INFO
     * Your funds will be deposited into FUND wallet by default. You can set the wallet for auto-transfer after deposit by this API.
     * Only main UID can access.
     * TIP
     * Unified trading account has FUND, UNIFIED, CONTRACT(for inverse derivatives)
     * Unified margin account has FUND, UNIFIED, CONTRACT(for inverse derivatives), SPOT
     * Normal account has FUND, OPTION(USDC account), CONTRACT(for inverse derivatives and derivatives), SPOT
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/set-deposit-acct">...</a>
     *
     * @param accountType
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/asset/deposit/deposit-to-account")
    Call<Object> setAssetDepositAccount(@Query("accountType") String accountType);

    /**
     * Get Deposit Records (on-chain)
     * Query deposit records.
     * <p>
     * TIP
     * endTime - startTime should be less than 30 days. Query last 30 days records by default.
     * Can use main or sub UID api key to query deposit records respectively.
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/deposit-record">...</a>
     *
     * @param coin
     * @param startTime
     * @param endTime
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/deposit/query-record")
    Call<Object> getAssetDepositRecords(
            @Query("coin") String coin,
            @Query("startTime") Long startTime,
            @Query("endTime") Long endTime,
            @Query("limit") Integer limit,
            @Query("cursor") String cursor);

    /**
     * Get Sub Deposit Records (on-chain)
     * Query subaccount's deposit records by main UID's API key.
     * <p>
     * TIP
     * endTime - startTime should be less than 30 days. Queries for the last 30 days worth of records by default.
     * <p> <a href="
     * ">* https://bybit-exchange.github.io/docs/v5/asset/sub-deposit-record</a>
     *
     * @param subMemberId
     * @param coin
     * @param startTime
     * @param endTime
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/deposit/query-sub-member-record")
    Call<Object> getAssetSubMembersDepositRecords(@Query("subMemberId") String subMemberId,
                                                  @Query("coin") String coin,
                                                  @Query("startTime") Long startTime,
                                                  @Query("endTime") Long endTime,
                                                  @Query("limit") Integer limit,
                                                  @Query("cursor") String cursor);

    /**
     * Get Internal Deposit Records (off-chain)
     * Query deposit records within the Bybit platform. These transactions are not on the blockchain.
     * <p>
     * RULES
     * The maximum difference between the start time and the end time is 30 days.
     * Support to get deposit records by Master or Sub Member Api Key
     * <p>
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/internal-deposit-record">...</a>
     *
     * @param coin
     * @param startTime
     * @param endTime
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/deposit/query-internal-record")
    Call<Object> getAssetInternalDepositRecords(@Query("coin") String coin,
                                                @Query("startTime") Long startTime,
                                                @Query("endTime") Long endTime,
                                                @Query("limit") Integer limit,
                                                @Query("cursor") String cursor);

    /**
     * Get Master Deposit Address
     * Query the deposit address information of MASTER account.
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/master-deposit-addr">...</a>
     *
     * @param coin
     * @param chainType
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/deposit/query-address")
    Call<Object> getAssetMasterDepositAddress(@Query("coin") String coin, @Query("chainType") String chainType);


    /**
     * Get Sub Deposit Address
     * Query the deposit address information of SUB account.
     * <p>
     * CAUTION
     * Can use master UID's api key only
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/sub-deposit-addr">...</a>
     *
     * @param coin
     * @param chainType
     * @param subMemberId
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/deposit/query-sub-member-address")
    Call<Object> getAssetSubMemberDepositAddress(@Query("coin") String coin,
                                                 @Query("chainType") String chainType,
                                                 @Query("subMemberId") String subMemberId);

    /**
     * Get Coin Info
     * Query coin information, including chain information, withdraw and deposit status.
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/coin-info">...</a>
     *
     * @param coin
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/coin/query-info")
    Call<Object> getAssetCoinInfo(@Query("coin") String coin);

    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/coin/query-info")
    Call<Object> getAssetCoinInfo();

    /**
     * Get Withdrawable Amount
     * INFO
     * How can partial funds be subject to delayed withdrawal requests?
     * <p>
     * On-chain deposit: If the number of on-chain confirmations has not reached a risk-controlled level, a portion of the funds will be frozen for a period of time until they are unfrozen.
     * Buying crypto: If there is a risk, the funds will be frozen for a certain period of time and cannot be withdrawn.
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/delay-amount">...</a>
     *
     * @param coin
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/withdraw/withdrawable-amount")
    Call<Object> getAssetWithdrawalAmount(@Query("coin") String coin);


    /**
     * Get Withdrawal Records
     * Query withdrawal records.
     * <p>
     * TIP
     * endTime - startTime should be less than 30 days. Query last 30 days records by default.
     * Can query by the master UID's api key only
     * <a href="https://bybit-exchange.github.io/docs/v5/asset/withdraw-record">...</a>
     *
     * @param withdrawID
     * @param coin
     * @param withdrawType
     * @param startTime
     * @param endTime
     * @param limit
     * @param cursor
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
    @GET("/v5/asset/withdraw/query-record")
    Call<Object> getAssetWithdrawalRecords(
            @Query("withdrawID") String withdrawID,
            @Query("coin") String coin,
            @Query("withdrawType") Integer withdrawType,
            @Query("startTime") Long startTime,
            @Query("endTime") Long endTime,
            @Query("limit") Integer limit,
            @Query("cursor") String cursor);

    /**
     * Cancel Withdrawal
     * Cancel the withdrawal
     * <p>
     * CAUTION
     * Can query by the master UID's api key only
     *
     * @param withdrawId
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/asset/withdraw/cancel")
    Call<Object> cancelAssetWithdraw(@Body String withdrawId);

    /**
     * Withdraw
     * Withdraw assets from your Bybit account. You can make an off-chain transfer if the target wallet address is from Bybit. This means that no blockchain fee will be charged.
     * <p>
     * DANGER
     * UTA does not have SPOT account
     * How do I know if my account is a UTA account? Call this endpoint, and if uta=1, then it is a UTA account.
     * CAUTION
     * Make sure you have whitelisted your wallet address here
     * Can query by the master UID's api key only
     * FORMULA
     * feeType=0:
     * <p>
     * withdrawPercentageFee != 0: handlingFee = inputAmount / (1 - withdrawPercentageFee) * withdrawPercentageFee + withdrawFee
     * withdrawPercentageFee = 0: handlingFee = withdrawFee
     * feeType=1:
     * <p>
     * withdrawPercentageFee != 0: handlingFee = withdrawFee + (inputAmount - withdrawFee) * withdrawPercentageFee
     * withdrawPercentageFee = 0: handlingFee = withdrawFee
     *
     * @param assetWithdrawRequest
     * @return
     */
    @Headers(BybitApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("/v5/asset/withdraw/create")
    Call<Object> createAssetWithdraw(@Body AssetWithdrawRequest assetWithdrawRequest);

    // Announcement

    /**
     * Get Announcement
     *
     * @param locale
     * @param type
     * @param tag
     * @param page
     * @param limit
     * @return
     */
    @GET("/v5/announcements/index")
    Call<Object> getAnouncementInfo(
            @Query("locale") String locale,
            @Query("type") String type,
            @Query("tag") String tag,
            @Query("page") Integer page,
            @Query("limit") Integer limit);
}
