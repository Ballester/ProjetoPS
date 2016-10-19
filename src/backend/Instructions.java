/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import static java.lang.System.exit;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author pedro
 */
public class Instructions {
    
    Memory memory;
    Scanner scanner;
    public Instructions() {
        this.memory = new Memory(1024);
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * transforms a value from indirect to direct
     * @return the direct position
     */
    public short indToDir(short pos) {
        return this.memory.load(pos);
    }
    
    public short indToIm(short pos) {
        return this.memory.load(this.memory.load(pos));
    }
    
    public short dirToIm(short pos) {
        return this.memory.load(pos);
    }
    
    /**
     * 
     * @param acc: value
     * @param op1: value
     * @return 
     */
    public void add(short acc, short op1) {
        acc = (short) (acc + op1);
    }
   
    /**
     * 
     * @param pc: valor
     * @param op1: valor
     */
    public void br(short pc, short op1) {
        pc = op1;
    } 
    
    public void brNeg(short pc, short op1, short acc) {
        if (acc < 0) {
            pc = op1;
        }
    }

    public void brPos(short pc, short op1, short acc) {
        if (acc > 0) {
            pc = op1;
        }
    }
    
    public void brZero(short pc, short op1, short acc) {
        if (acc == 0) {
            pc = op1;
        }
    }
    
    public void call(Stack sp, short pc, short op1) {
        sp.push(pc);
        pc = op1;
    }
    
    /**
     * 
     * @param pos1: direct memory position
     * @param pos2: direct memory position
     */
    public void copy(int pos1, int pos2) {
        this.memory.store(this.memory.load(pos2), pos1);
    }
    
    public void divide(short acc, short op1) {
        acc = (short) (acc/op1);
    }
    
    public void mult(short acc, short op1) {
        acc = (short) (acc*op1);
    }
    
    public short read() {
        return this.scanner.nextShort();
    }
    
    public void ret(Stack sp, short pc) {
        pc = (short) sp.pop();
    }
    
    public void stop() {
        exit(0);
    }
    
    public void sub(short acc, short op1) {
        acc = (short) (acc - op1);
    } 
    
    public void write(short op1) {
        System.out.println(op1);
    }
    
    /**
     * 
     * @param op1: value
     * @param pos: direct memory position
     */
    public void store(short op1, int pos) {
        this.memory.store(op1, pos);
    }
    
    /**
     * 
     * @param pos: access memory position 
     */
    public void load(short acc, short pos) {
        acc = pos;
    }
    
    
}
