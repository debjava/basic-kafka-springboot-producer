package com.ddlab.rnd.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddlab.rnd.entity.Employee;

@RestController
public class ProducerController {

	@Value(value = "${kafka.emp.topic.name}")
	private String objTopicName;
	
	@Value(value = "${kafka.str.topic.name}")
	private String strTopicName;

	@Autowired
	private KafkaTemplate<String, Employee> objKafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, String> stringKafkaTemplate;

	@PostMapping(path = "/publish/obj", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String postObjectMessage(@RequestBody Employee emp) {
		System.out.println("Employee : " + emp);
		objKafkaTemplate.send(objTopicName, emp);

		return "Employee Object Published Successfully";
	}
	
	@PostMapping(path = "/publish/str", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String postStringMessage(@RequestBody String msg) {
		System.out.println("String msg : " + msg);
		stringKafkaTemplate.send(strTopicName, msg);

		return "String Published Successfully";
	}
}
