package com.example.store.core;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Error {
    ERROR01("0001", "کاربری با مشخصات خواسته شده وجود ندارد"),
    ERROR02("0002", "کاربر غیر فعال است، با مدیریت تماس بگیرید"),
    ERROR03("0003", "لطفا نام کاربری را وارد کنید"),
    ERROR04("0004", "لطفا پسورد را وارد کنید"),
    ERROR05("0005", "طول رمز عبور باید بیش از 6 کاراکتر باشد"),
    ERROR06("0006", "نام دسته بندی را وارد کنید"),
    ERROR07("0007", "طول دسته بندی بیش از 2 کارکتر باشد"),
    ERROR_READ_CATEGORY("0008", "مشکل در فراخوانی دسته بندی"),
    ERROR_PRODUCT_NAME("0009", "لطفا نام محصول را وارد کنید"),
    ERROR0_PRODUCT_NAME_LENGTH("00010", "طول دسته بندی بیش از 2 کارکتر باشد"),
    ERROR0_PRODUCT_PRICE_COUNT("00010", "طول دسته بندی بیش از 2 کارکتر باشد");

    final String code;
    final String message;
}
