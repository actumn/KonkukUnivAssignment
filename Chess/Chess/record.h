#pragma once
#include <string>

class Piece;
class Record {
public:
	std::string name;
	int preI, preJ;
	int nextI, nextJ;
	std::string deadObj;
	int objI, objJ;
	Record(std::string name, int preI, int preJ, int nextI, int nextJ, Piece* deadObj);
	~Record();

};