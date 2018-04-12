package org.wikipedia.userstatistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.junit.Test;
import org.wikipedia.userstatistics.StatisticFragment;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by KammyWong on 2018-04-12.
 */

public class StatisticFragmentTest {

    @Test
    public void newInstanceTest(){
        StatisticFragment statFragment = mock(StatisticFragment.class);
        Fragment fragment = statFragment.newInstance();

        LayoutInflater layoutInflater = mock(LayoutInflater.class);
        ViewGroup viewGroup = mock(ViewGroup.class);
        Bundle bundle = mock(Bundle.class);

        verify(statFragment).newInstance();
    }
}
