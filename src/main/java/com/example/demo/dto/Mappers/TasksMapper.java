package com.example.demo.dto.Mappers;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.dto.ResponseTasks;
import com.example.demo.entity.SubTasks;
import com.example.demo.entity.Tasks;

public class TasksMapper {

	public static ResponseTasks toTasks(Tasks obj) {

		Set<SubTasks> list = new HashSet<>();

		Integer pending = 0;
		Integer feats = 0;

		for (SubTasks st : obj.getSubtasks()) {
			if (st.getCheckbox()) {
				feats += 1;
			} else {
				pending += 1;
			}
		}

		ResponseTasks task = new ResponseTasks(obj.getId(), obj.getTitle(), obj.getDescription(), obj.getSubtasks(),
				pending, feats);

		return task;

	}

}
