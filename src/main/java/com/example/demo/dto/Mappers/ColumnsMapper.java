package com.example.demo.dto.Mappers;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.dto.ColumnDTO;
import com.example.demo.dto.ResponseColumnDTO;
import com.example.demo.dto.TasksColumnDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Column;
import com.example.demo.entity.SubTasks;
import com.example.demo.entity.Tasks;

public class ColumnsMapper {

	public static ResponseColumnDTO toColumn(Board obj) {

		Set<ColumnDTO> list = new HashSet<>();

		Integer pending = 0;
		Integer feats = 0;

		for (Column col : obj.getColumns()) {
			Set<TasksColumnDTO> listTasks = new HashSet<>();

			for (Tasks tk : col.getTasks()) {

				for (SubTasks st : tk.getSubtasks()) {

					if (st.getCheckbox()) {
						feats += 1;
					} else {
						pending += 1;
					}

				}

				TasksColumnDTO tkDTO = new TasksColumnDTO(tk.getId(), tk.getTitle(), pending, feats);
				listTasks.add(tkDTO);

			}

			ColumnDTO colDTO = new ColumnDTO(col.getId(), col.getCor(), col.getName(), listTasks);
			list.add(colDTO);
		}

		ResponseColumnDTO newObj = new ResponseColumnDTO(obj.getId(), obj.getName(), list);

		return newObj;
	}

}
