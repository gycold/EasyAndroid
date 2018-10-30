package com.easytools.tools;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * package: com.easytools.tools.MemoryFileHelper
 * author: gyc
 * description:
 * 对MemoryFile类的扩展，使用共享内存进行IPC通信:
 * 1.从memoryFile对象中获取FileDescriptor,ParcelFileDescriptor
 * 2.根据一个FileDescriptor和文件length实例化MemoryFile对象
 * time: create at 2018/5/3 10:23
 */

public class MemoryFileHelper {
    /**
     * 创建共享内存对象
     * @param name 描述共享内存文件名称
     * @param length 用于指定创建多大的共享内存对象
     * @return MemoryFile 描述共享内存对象
     */
    public static MemoryFile createMemoryFile(String name, int length){
        try {
            return new MemoryFile(name,length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打开共享内存
     * @param pfd
     * @param length
     * @param mode
     * @return
     */
    public static MemoryFile openMemoryFile(ParcelFileDescriptor pfd, int length, int mode){
        if(pfd == null){
            throw new IllegalArgumentException("ParcelFileDescriptor 不能为空");
        }
        FileDescriptor fd = pfd.getFileDescriptor();
        return openMemoryFile(fd,length,mode);
    }

    /**
     * 打开共享内存，一般是一个地方创建了一块共享内存
     * 另一个地方持有描述这块共享内存的文件描述符，调用
     * 此方法即可获得一个描述那块共享内存的MemoryFile
     * 对象
     * @param fd 文件描述
     * @param length 共享内存的大小
     * @param mode PROT_READ = 0x1只读方式打开,
     *             PROT_WRITE = 0x2可写方式打开，
     *             PROT_WRITE|PROT_READ可读可写方式打开
     * @return MemoryFile
     */
    public static MemoryFile openMemoryFile(FileDescriptor fd,int length,int mode){
        MemoryFile memoryFile = null;
        try {
            memoryFile = new MemoryFile("tem",1);
            memoryFile.close();
            Class<?> c = MemoryFile.class;
            Method native_mmap = null;
            Method[] ms = c.getDeclaredMethods();
            for(int i = 0;ms != null&&i<ms.length;i++){
                if(ms[i].getName().equals("native_mmap")){
                    native_mmap = ms[i];
                }
            }
            ReflectUtils.setField("android.os.MemoryFile", memoryFile, "mFD", fd);
            ReflectUtils.setField("android.os.MemoryFile",memoryFile,"mLength",length);
            long address = (long) ReflectUtils.invokeMethod( null, native_mmap, fd, length, mode);
            ReflectUtils.setField("android.os.MemoryFile", memoryFile, "mAddress", address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memoryFile;
    }

    /**
     * 获取共享内存的文件描述符
     * @param memoryFile 描述一块共享内存
     * @return ParcelFileDescriptor
     */
    public static ParcelFileDescriptor getParcelFileDescriptor(MemoryFile memoryFile){
        if(memoryFile == null){
            throw new IllegalArgumentException("memoryFile 不能为空");
        }
        ParcelFileDescriptor pfd;
        FileDescriptor fd = getFileDescriptor(memoryFile);
        pfd = (ParcelFileDescriptor) ReflectUtils.getInstance("android.os.ParcelFileDescriptor",fd);
        return pfd;
    }

    /**
     * 获取共享内存的文件描述符
     * @param memoryFile 描述一块共享内存
     * @return 这块共享内存对应的文件描述符
     */
    public static FileDescriptor getFileDescriptor(MemoryFile memoryFile){
        if(memoryFile == null){
            throw new IllegalArgumentException("memoryFile 不能为空");
        }
        FileDescriptor fd;
        fd = (FileDescriptor) ReflectUtils.invoke("android.os.MemoryFile",memoryFile,"getFileDescriptor");
        return fd;
    }
}
