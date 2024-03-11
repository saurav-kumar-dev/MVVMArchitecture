package com.codingwithsaurav.mvvmarchitecture.base

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflate<VB> = (LayoutInflater, ViewGroup, Boolean) -> VB
