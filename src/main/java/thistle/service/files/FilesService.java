package thistle.service.files;

import java.io.InputStream;

public interface FilesService {

    void saveFile(InputStream inputStream, String md5);
}
