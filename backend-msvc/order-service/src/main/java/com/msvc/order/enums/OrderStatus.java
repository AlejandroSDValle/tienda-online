package com.msvc.order.enums;

public enum OrderStatus {
    PLACED,             // 1. The customer placed the order
    QUEUED,             // 2. It is in queue
    PREPARING,          // 3. The order is being prepared
    READY,              // 4. order fully prepared
    AVAILABLE_FOR_PICKUP, // 5. Can now be picked up in store
    PICKED_UP,          // 6. it has already been picked up by the customer
    CLAIMED,            // 7. The customer made a claim
    CANCELLED           // 8. Order was canceled
}