import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import problem.AppLauncherBaseCaseTest;
import problem.AppLauncherSupportTest;
import problem.DataFileRunnerTest;
import problem.DirectoryChangeLoggerTest;
import problem.DirectoryEventTest;
import problem.ExecutableFileRunnerTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	LibraryTest.class
})
public class TestAll {
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
