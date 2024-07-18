package dev.patika.vetmanagement.core.utilities;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import org.springframework.data.domain.Page;

public class ResultHelper {
    public static <T> ResultData<T> created(T data){
        return new ResultData<>(Message.CREATED,"201",true,data);
    }
    public static <T> ResultData<T> validateError(T data){
        return new ResultData<>(Message.VALIDATE_ERROR,"404",false,data);
    }
    public static <T> ResultData<T> success(T data){
        return new ResultData<>(Message.OK,"200",true,data);
    }
    public static Result ok(){
        return new Result(Message.OK,"200",true);
    }
    public static Result notFoundError(String msg){
        return new Result(msg,"404",false);
    }
    public static <T> ResultData<CursorResponse<T>> cursor(Page<T> pageData){
        CursorResponse<T> cursor = new CursorResponse<>();
        cursor.setItems(pageData.getContent());
        cursor.setPageNumber(pageData.getNumber());
        cursor.setPageSize(pageData.getSize());
        cursor.setTotalElements(pageData.getTotalElements());
        return ResultHelper.success(cursor);
    }
}
