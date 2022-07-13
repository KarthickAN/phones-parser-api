package com.karthick.io.phones.parser.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tasks")
public class Task {

	@Id
	@GeneratedValue
	@Type(type = "org.hibernate.type.UUIDCharType")
	private UUID taskId;

	private long startTime;

	private long endTime;

	private Status status;

	private long totalEntries;

	private String fileName;

	private String startedBy;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "taskId")
	private List<PhoneNumbers> phoneNumbers;

}
