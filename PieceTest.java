package tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s1, s1Rotated;
	private Piece s2, s2Rotated;
	private Piece l1, l1R1, l1R2, l1R3;
	private Piece l2, l2R1, l2R2, l2R3;
	private Piece stick, stickRotated;
	private Piece square;

	@Before
	public void setUp() throws Exception {
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s1 = new Piece(Piece.S1_STR);
		s1Rotated = s1.computeNextRotation();
		
		s2 = new Piece(Piece.S2_STR);
		s2Rotated = s2.computeNextRotation();
		
		l1 = new Piece(Piece.L1_STR);
		l1R1 = l1.computeNextRotation();
		l1R2 = l1R1.computeNextRotation();
		l1R3 = l1R2.computeNextRotation();
		
		l2 = new Piece(Piece.L2_STR);
		l2R1 = l2.computeNextRotation();
		l2R2 = l2R1.computeNextRotation();
		l2R3 = l2R2.computeNextRotation();
		
		stick = new Piece(Piece.STICK_STR);
		stickRotated = stick.computeNextRotation();
		
		square = new Piece(Piece.SQUARE_STR);
	}
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize() {
		// Check size of pyr piece
		
		
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here

		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		assertEquals(3, pyr1.getWidth());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	@Test
	public void testWidth() {
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr3.getWidth());
		assertEquals(2, pyr4.getWidth());
		
		assertEquals(3, s1.getWidth());
		assertEquals(2, s1Rotated.getWidth());
		
		assertEquals(3, s2.getWidth());
		assertEquals(2, s2Rotated.getWidth());
		
		assertEquals(2, l1.getWidth());
		assertEquals(3, l1R1.getWidth());
		assertEquals(2, l1R2.getWidth());
		assertEquals(3, l1R3.getWidth());
		
		assertEquals(2, l2.getWidth());
		assertEquals(3, l2R1.getWidth());
		assertEquals(2, l2R2.getWidth());
		assertEquals(3, l2R3.getWidth());
		
		assertEquals(1, stick.getWidth());
		assertEquals(4, stickRotated.getWidth());
		
		assertEquals(2, square.getWidth());	
	}
	
	@Test
	public void testHeight(){
		assertEquals(2, pyr1.getHeight());
		assertEquals(3, pyr2.getHeight());
		assertEquals(2, pyr3.getHeight());
		assertEquals(3, pyr4.getHeight());
		
		assertEquals(2, s1.getHeight());
		assertEquals(3, s1Rotated.getHeight());
		
		assertEquals(2, s2.getHeight());
		assertEquals(3, s2Rotated.getHeight());
		
		assertEquals(3, l1.getHeight());
		assertEquals(2, l1R1.getHeight());
		assertEquals(3, l1R2.getHeight());
		assertEquals(2, l1R3.getHeight());
		
		assertEquals(3, l2.getHeight());
		assertEquals(2, l2R1.getHeight());
		assertEquals(3, l2R2.getHeight());
		assertEquals(2, l2R3.getHeight());
		
		assertEquals(4, stick.getHeight());
		assertEquals(1, stickRotated.getHeight());
		
		assertEquals(2, square.getHeight());	
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
//		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
//		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, s1Rotated.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0}, square.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, stick.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, stickRotated.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, l1.getSkirt()));
		
	}
	
	@Test
	public void testEquals() {
		Piece squareTest = new Piece(Piece.SQUARE_STR);
		Piece squareTest2 = new Piece("1 1  1 0  0 1  0 0");
		
		Piece stickTest = new Piece(Piece.STICK_STR);
		Piece stickTest2 = new Piece("3 0  2 0  1 0  0 0");
		
		assertTrue(square.equals(squareTest));
		assertTrue(square.equals(squareTest2));
		
		assertFalse(square.equals(stickTest));
		assertFalse(square.equals(stickTest2));
	}
	
//	@Test
//	public void testMakeFastRotations() {
//		Piece.makeFastRotations(stick);
//		
//		
//	}
//	
	@Test
	public void testGetPieces() {
		Piece[] pieces = Piece.getPieces();
		
		assertTrue(pieces[0].fastRotation().equals(stickRotated));	
		assertTrue(pieces[0].fastRotation().fastRotation().equals(stick));
		
		assertTrue(pieces[1].fastRotation().equals(l1R1));	
		assertTrue(pieces[1].fastRotation().fastRotation().equals(l1R2));
		assertTrue(pieces[1].fastRotation().fastRotation().fastRotation().equals(l1R3));
		assertTrue(pieces[1].fastRotation().fastRotation().fastRotation().fastRotation().equals(l1));
		
	}
	
	
}
