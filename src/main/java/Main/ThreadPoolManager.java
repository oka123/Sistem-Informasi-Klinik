/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {

    // 1. Instance Static (Tunggal untuk satu aplikasi)
    private static ThreadPoolManager instance;
    
    // 2. Variabel Executor (Kolom Thread)
    private final ExecutorService executor;

    // 3. Constructor Private
    // (Supaya tidak ada kelas lain yang bisa men-new ThreadPoolManager() sembarangan)
    private ThreadPoolManager() {
        // Sesuai permintaan Anda: SingleThreadExecutor
        // ATAU gunakan newFixedThreadPool(4) jika ingin multitasking (lebih disarankan untuk app besar)
        this.executor = Executors.newSingleThreadExecutor(); 
    }

    // 4. Method Akses Global (Pintu Masuk)
    public static synchronized ThreadPoolManager getInstance() {
        if (instance == null) {
            instance = new ThreadPoolManager();
        }
        return instance;
    }

    // 5. Method untuk mengirim tugas (Task)
    public void submit(Runnable task) {
        executor.submit(task);
    }
    
    // Opsional: Matikan pool saat aplikasi ditutup
    public void shutdown() {
        executor.shutdown();
        instance = null;
    }
}
