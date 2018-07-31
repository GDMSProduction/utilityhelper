package com.zammle2009wtfgmail.utilityhelper;

import java.util.List;

public interface UsageContract {

    interface View{

        void onFilteredStatsRetrieved(List<UsageStatsWrapper> filteredList);
        void onUsageStatsRetrieved(List<UsageStatsWrapper> list);
        void onUserHasNoPermission();
    }

    interface Presenter{
        void retrieveUsageStats();
    }
}
