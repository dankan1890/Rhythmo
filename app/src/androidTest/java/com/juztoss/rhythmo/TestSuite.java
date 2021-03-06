package com.juztoss.rhythmo;

import com.juztoss.rhythmo.views.activities.FileWasDeleted;
import com.juztoss.rhythmo.views.activities.OperationsWithPlaylists;
import com.juztoss.rhythmo.views.activities.PlayerActivityTest;
import com.juztoss.rhythmo.views.activities.SearchTest;
import com.juztoss.rhythmo.views.activities.SortsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by JuzTosS on 8/21/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CopyMediaToDevice.class,
        TouchToWakeUp.class,

        SettingsTest.class,
        OperationsWithPlaylists.class,
        FileWasDeleted.class,
        PlayerActivityTest.class,
        SearchTest.class,
        SortsTest.class,

        RemoveMediaFromDevice.class,
})

public class TestSuite
{

}
