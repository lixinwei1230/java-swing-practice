import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuM_ on 9/26/16.
 */
@XmlRootElement(name = "lines")
@XmlAccessorType(XmlAccessType.FIELD)
public class LineLists {

    @XmlElement(name = "line")
    private List<Line> lines = null;

    public List<Line> getLines() {
        return lines;
    }
    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
