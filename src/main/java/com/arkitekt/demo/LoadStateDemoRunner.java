package com.arkitekt.demo;

import com.arkitekt.domain.State;
import com.arkitekt.io.StateReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadStateDemoRunner implements CommandLineRunner {
    @Autowired
    private StateReader reader;

    @Override
    public void run(String... strings) throws Exception {
        State state = reader.load("./assets/turn3_state.txt");
        System.out.println(state);
    }
}
