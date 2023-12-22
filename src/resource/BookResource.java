package resource;

import bean.ApiResponce;
import bean.BookBean;
import db.DB;

import java.util.List;

public class BookResource implements BaseCrudResource<BookBean> {
    @Override
    public ApiResponce create(BookBean bean) {
        return null;
    }

    @Override
    public ApiResponce get(Integer id) {
        BookBean book=DB.getBook(id);

        if(book==null){
            return new ApiResponce(400,"book not found",null);
        }else {
            return new ApiResponce(200,"book found",book);
        }

    }

    @Override
    public ApiResponce update(BookBean bean) {
        return null;
    }

    @Override
    public ApiResponce delete(Integer id) {
        return null;
    }

    public ApiResponce getAvailableBooks() {
        List<BookBean> availableBooks = DB.getAvailableBooks();

        return new ApiResponce(200,"Success",availableBooks);
    }

    public ApiResponce getUsersBooks() {
        List<BookBean> usersBooks = DB.getUsersBooks();

        return new ApiResponce(200,"Success",usersBooks);
    }

    public ApiResponce returnBook(Integer bookId) {
        boolean b = DB.returnBook(bookId);

        return new ApiResponce(b?200:400,b?"success":"error",b);
    }
}
