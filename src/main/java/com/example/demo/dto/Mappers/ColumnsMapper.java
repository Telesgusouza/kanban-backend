package com.example.demo.dto.Mappers;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.dto.ColumnDTO;
import com.example.demo.dto.ResponseColumnDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Column;

public class ColumnsMapper {

	public static ResponseColumnDTO toColumn(Board obj) {

		Set<ColumnDTO> list = new HashSet<>();

		for (Column col : obj.getColumns()) {
			ColumnDTO colDTO = new ColumnDTO(col.getId(), col.getCor(), col.getName());
			list.add(colDTO);
		}

		ResponseColumnDTO newObj = new ResponseColumnDTO(obj.getId(), obj.getName(), list);

		return newObj;
	}

}
