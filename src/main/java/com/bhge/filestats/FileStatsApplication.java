package com.bhge.filestats;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(Processor.class)
@SpringBootApplication
public class FileStatsApplication {
	
	@Autowired
	FileStatsRepository fileStatsRepository;

	public static void main(String[] args) {
		SpringApplication.run(FileStatsApplication.class, args);
	}
	
	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Object saveFileStats(Message<?> message) {
		System.out.println(message);
		MessageHeaders header = message.getHeaders();
		FileStats fileStats = new FileStats();
		fileStats.setName(header.get("file_name").toString());
		fileStats.setPath(header.get("file_originalFile").toString());
		fileStats.setTimestamp(LocalDateTime.now().toString());
		byte[] body = (byte[])message.getPayload();
		fileStats.setSize(body.length);
		fileStatsRepository.save(fileStats);
		return message;
	}
}
