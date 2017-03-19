/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author pedro
 */
public class Memory {
    
    public String[] memory;
    public int size;
    public Memory(int size) {
        this.memory = new String[size];
        this.size = size;
    }
    
    public void store(String value, int pos) {
        this.memory[pos] = value;
    }
    
    public void store(String value, short pos) {
        this.memory[pos] = value;
    }
    
    public String load(short pos) {
        return this.memory[pos];
    }
    
    public Byte loadByte(short pos) {
        return Byte.parseByte(this.memory[pos], 2);
    }
    
}
