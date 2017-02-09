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
    
    public short[] memory;
    public int size;
    public Memory(int size) {
        this.memory = new short[size];
        this.size = size;
    }
    
    public void store(short value, int pos) {
        this.memory[pos] = value;
    }
    
    public void store(short value, short pos) {
        this.memory[pos] = value;
    }
    
    public short load(int pos) {
        return this.memory[pos];
    }
    
}
