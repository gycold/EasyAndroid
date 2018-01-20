package com.easytools.tools;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;

/**
 * package: com.easytools.tools.FragmentUtil
 * author: gyc
 * description:封装Fragment相关的工具类(注：不是v4包中的Fragment)
 * time: create at 2017/2/2 22:56
 */

public class FragmentUtil {

    private FragmentUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 替换Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param container       要被替换的Fragment的布局ID
     * @param newFragment     新的Fragment
     * @param addToBackStack  是否添加到回退栈
     * @return
     */
    public static Fragment replaceFragment(FragmentManager fragmentManager,
                                           int container, Fragment newFragment, boolean addToBackStack) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final String tag = newFragment.getClass().getSimpleName();
        if (newFragment != null) {
            transaction.replace(container, newFragment, tag);
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        return newFragment;
    }

    /**
     * 替换Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param container       要被替换的Fragment的布局ID
     * @param newFragment     新的Fragment
     * @return
     */
    public static Fragment replaceFragment(FragmentManager fragmentManager,
                                           @IdRes int container,
                                           Fragment newFragment) {
        return replaceFragment(fragmentManager, container, newFragment, false);
    }

    /**
     * 替换Fragment，携带参数
     *
     * @param fragmentManager Fragment管理器
     * @param container       要被替换的Fragment的布局ID
     * @param newFragment     新的Fragment
     * @param args            携带参数
     * @return
     */
    public static Fragment replaceFragment(FragmentManager fragmentManager,
                                           @IdRes int container,
                                           Class<? extends Fragment> newFragment,
                                           Bundle args) {
        return replaceFragment(fragmentManager, container, newFragment, args, false);
    }

    /**
     * 替换Fragment，携带参数
     *
     * @param fragmentManager Fragment管理器
     * @param container       要被替换的Fragment的布局ID
     * @param newFragment     新的Fragment
     * @param args            携带参数
     * @param addToBackStack  是否添加到回退栈
     * @return
     */
    public static Fragment replaceFragment(FragmentManager fragmentManager, int container,
                                           Class<? extends Fragment> newFragment, Bundle args, boolean addToBackStack) {
        Fragment fragment = null;
        //构造新的Fragment
        try {
            fragment = newFragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            if (args != null && !args.isEmpty()) {
                final Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putAll(args);
                } else {
                    fragment.setArguments(args);
                }
            }
            //替换
            return replaceFragment(fragmentManager, container, fragment, addToBackStack);
        } else {
            return null;
        }
    }

    /**
     * 切换Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param container       要被替换的Fragment的布局ID
     * @param currentFragment 当前的Fragment
     * @param newFragment     新的Fragment
     * @param args            携带参数
     * @return
     */
    public static Fragment switchFragment(FragmentManager fragmentManager,
                                          int container,
                                          Fragment currentFragment,
                                          Class<? extends Fragment> newFragment,
                                          Bundle args) {
        return switchFragment(fragmentManager, container, currentFragment, newFragment, args, false);
    }

    /**
     * 切换Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param container       要被替换的Fragment的布局ID
     * @param currentFragment 当前的Fragment
     * @param newFragment     新的Fragment
     * @param args            携带参数
     * @param addToBackStack  是否添加到回退栈
     * @return
     */
    public static Fragment switchFragment(FragmentManager fragmentManager, @IdRes int container, Fragment currentFragment,
                                          Class<? extends Fragment> newFragment, Bundle args, boolean addToBackStack) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final String tag = newFragment.getSimpleName();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        // 如果在栈中找到相应的Fragment，则显示，否则重新生成一个
        if (fragment != null) {
            if (fragment != currentFragment) {
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.show(fragment);
                if (addToBackStack) {
                    transaction.addToBackStack(null);
                }
                transaction.commitAllowingStateLoss();
            } else {
                fragment.getArguments().putAll(args);
            }
            return fragment;
        } else {
            try {
                fragment = newFragment.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 为新的Fragment添加参数
        if (fragment != null) {
            if (args != null && !args.isEmpty()) {
                final Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putAll(args);
                } else {
                    fragment.setArguments(args);
                }
            }
        }
        // 显示新的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.add(container, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        return fragment;
    }
}
