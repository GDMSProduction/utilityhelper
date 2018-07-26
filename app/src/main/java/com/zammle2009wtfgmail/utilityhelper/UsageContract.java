package com.zammle2009wtfgmail.utilityhelper;

import java.util.List;

public interface UsageContract {

    interface View{
        boolean onQueryTextChange(String query);

        boolean onQueryTextSubmit(String query);

        void onEditStarted();

        void onEditFinished();

        void onFilteredStatsRetrieved(List<UsageStatsWrapper> filteredList);
        void onUsageStatsRetrieved(List<UsageStatsWrapper> list);
        void onUserHasNoPermission();
    }

    interface Presenter{
        void retrieveUsageStats();
    }
}
