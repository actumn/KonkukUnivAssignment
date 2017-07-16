#include "record.h"
#include "Piece.h"

Record::Record(std::string name, int preI, int preJ, int nextI, int nextJ, Piece* deadObj) {
	this->name = name;
	this->preI = preI;
	this->preJ = preJ;
	this->nextI = nextI;
	this->nextJ = nextJ;
	if (deadObj == NULL) {
		this->deadObj = "";
		this->objI = 8;
		this->objJ = 8;
	}
	else {
		this->deadObj = deadObj->getName();
		this->objI = deadObj->getI();
		this->objJ = deadObj->getJ();
	}
}

Record::~Record() {

}
